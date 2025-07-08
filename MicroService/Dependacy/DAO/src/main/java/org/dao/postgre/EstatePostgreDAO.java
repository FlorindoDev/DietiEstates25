package org.dao.postgre;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.dao.Interfacce.AgentDAO;
import org.dao.Interfacce.EstateDAO;
import org.exc.DataBaseException.AddressAlreadyExists;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.EstateNotExists;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.ClasseEnergetica.ConverterEnergeticClass;
import org.md.Estate.ClasseEnergetica.EnergeticClass;
import org.md.Estate.Estate;
import org.md.Estate.EstateFilter;
import org.md.Estate.Mode.ConverterMode;
import org.md.Estate.Mode.Mode;
import org.md.Estate.Status.ConverterStatus;
import org.md.Estate.Status.Status;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Utente.Agent;

import DBLib.Postgres.CommunicationWithPostgre;

public class EstatePostgreDAO implements EstateDAO {

    private static final String TABLE = "immobile";
    protected static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private int numberParamsOfFilter = 1;
    private CommunicationWithPostgre connection;

    private static final Logger logger = Logger.getLogger(EstatePostgreDAO.class.getName());

    private final AgentDAO agentDAO;

    public EstatePostgreDAO() {
        this.connection = new CommunicationWithPostgre();
        this.agentDAO = new AgentPostgreDAO();
    }

    public void insertImg(int idEstate, String foto) throws SQLException{
        String query = """
          INSERT INTO fotoimmobile (foto, idimmobile)
          VALUES (?, ?)
        """;

        try (PreparedStatement stmtFoto = connection.getStatment(query)) {
            stmtFoto.setString(1, foto);
            stmtFoto.setInt(2, idEstate);
            stmtFoto.executeUpdate();
        }

    }

    @Override
    public void createEstate(Estate newEstate) throws DietiEstateException {

        String query = "INSERT INTO " + TABLE + " (" +
                "idagente, idindirizzo, partitaIVA, descrizione, prezzo, dimensioni, stanze, piano, bagni, garage, ascensore, classeenergetica, modalita, stato) VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                "RETURNING idimmobile";

        connection.setAutoCommit(false);

        IndirizzoPostgreDAO addrsDao = new IndirizzoPostgreDAO(connection);
        AgentPostgreDAO agentsDao = new AgentPostgreDAO();

        int idAgente = newEstate.getAgente().getIdUser();
        if (idAgente == 0) {
            idAgente = agentDAO.getUser(newEstate.getAgente()).getIdUser();
            newEstate.getAgente().setIdUser(idAgente);
        }
        agentsDao.isUserPresent(newEstate.getAgente());

        int idAddress = 0;
        try {
            try {
                addrsDao.isAddressNotExistsByALL(newEstate.getIndirizzo()); // vuole crearlo, verifico se gai esiste
                // se non solleva eccezioni significa che non esiste, procedo a crarlo
                addrsDao.createAddress(newEstate.getIndirizzo());
                idAddress = addrsDao.getLastAddressId();

            } catch (AddressAlreadyExists e) {
                logger.info("sono nel catch");
                try {
                    connection.nextRow();
                } catch (SQLException e1) {
                    logger.severe(ERROR_EXECUTING_QUERY + e1.getMessage());
                    throw new ErrorExecutingQuery();
                }
                logger.info("1");
                idAddress = connection.extractInt("idindirizzo");
                logger.info("2");
            }

            try (PreparedStatement stmt = connection.getStatment(query)) {

                int index = 0;

                stmt.setInt(++index, newEstate.getAgente().getIdUser());
                stmt.setInt(++index, idAddress);
                stmt.setString(++index, newEstate.getAgenzia().getCodicePartitaIVA());

                stmt.setString(++index, newEstate.getDescrizione());
                stmt.setDouble(++index, newEstate.getPrice());
                stmt.setDouble(++index, newEstate.getSpace());
                stmt.setInt(++index, newEstate.getRooms());
                stmt.setInt(++index, newEstate.getFloor());
                stmt.setInt(++index, newEstate.getWc());
                stmt.setInt(++index, newEstate.getGarage());
                stmt.setBoolean(++index, newEstate.getElevator());
                stmt.setString(++index, newEstate.getClasseEnergetica().getNome());
                stmt.setObject(++index, newEstate.getMode().getName(), Types.OTHER);
                stmt.setObject(++index, newEstate.getStato().getName(), Types.OTHER);

                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) throw new ErrorExecutingQuery();
                newEstate.setIdEstate(rs.getInt("idimmobile"));

                for (String foto : newEstate.getFoto()) {
                    insertImg(newEstate.getIdEstate(), foto);
                }

                connection.commitActions();

            } catch (SQLException e) {
                logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
                throw new ErrorExecutingQuery();
            }

        }catch (Exception e){
            connection.rollBackAction();
            throw new ErrorExecutingQuery();
        }finally {
            connection.setAutoCommit(true);
        }

    }

    @Override
    public void updateEstate(Estate estate) throws DietiEstateException{

        StringBuilder query = new StringBuilder(String.format("UPDATE %s SET ", TABLE));
        List<Object> params = new ArrayList<>();

        if (estate.getAgente() != null) {
            query.append("idagente = ?, ");
            params.add(estate.getAgente().getIdUser());
        }
        if (estate.getIndirizzo() != null) {
            query.append("idindirizzo = ?, ");
            params.add(estate.getIndirizzo().getIdIndirizzo());
        }
        if (estate.getAgenzia() != null) {
            query.append("partitaiva = ?, ");
            params.add(estate.getAgenzia().getCodicePartitaIVA());
        }
        // if (!estate.getFoto().isEmpty()) {
        //     query.append("foto = ?, ");
        //     params.add(estate.getFoto());
        // }
        if (!estate.getDescrizione().isEmpty()) {
            query.append("descrizione = ?, ");
            params.add(estate.getDescrizione());
        }
        if (estate.getPrice() != 0) {
            query.append("prezzo = ?, ");
            params.add(estate.getPrice());
        }
        if (estate.getSpace() != 0) {
            query.append("dimensioni = ?, ");
            params.add(estate.getSpace());
        }
        if (estate.getRooms() != 0) {
            query.append("stanze = ?, ");
            params.add(estate.getRooms());
        }

        if (params.isEmpty()) {
            return; // Nessun aggiornamento necessario
        }

        query.setLength(query.length() - 2); // Rimuove l'ultima virgola
        query.append(" WHERE idimmobile = ?");
        params.add(estate.getIdEstate());


        try {

            PreparedStatement stmt = connection.getStatment(String.valueOf(query));

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            connection.makeQueryUpdate(stmt);

        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public List<Estate> getEstates() throws DietiEstateException{

        String query = """
            SELECT
              i.idimmobile AS i_idimmobile,
              i.idagente AS i_idagente,
              i.idindirizzo AS i_idindirizzo,
              i.partitaiva AS i_partitaiva,
              i.descrizione AS i_descrizione,
              i.prezzo AS i_prezzo,
              i.dimensioni AS i_dimensioni,
              i.stanze AS i_stanze,
              i.piano AS i_piano,
              i.bagni AS i_bagni,
              i.garage AS i_garage,
              i.ascensore AS i_ascensore,
              i.classeenergetica AS i_classeenergetica,
              i.modalita AS i_modalita,
              i.stato AS i_stato,
            
              a.idagente AS a_idagente,
              a.email AS a_email,
              a.biografia AS a_biografia,
              a.immagineprofilo AS a_immagineprofilo,
              a.nome AS a_nome,
              a.cognome AS a_cognome,
              a.password AS a_password,
              a.partitaiva AS a_partitaiva,
              a.idpushnotify AS a_idpushnotify,
              a.notify_appointment AS a_notify_appointment,
            
              ag.partitaiva AS ag_partitaiva,
              ag.nome AS ag_nome,
              ag.sede AS ag_sede
            
            FROM immobile i
            JOIN agenziaimmobiliare ag ON i.partitaiva = ag.partitaiva
            JOIN agenteimmobiliare a ON i.idagente = a.idagente
        """;

        PreparedStatement stmt = connection.getStatment(query);
        List<Estate> estates = new ArrayList<Estate>();
        try{
            connection.makeQuery(stmt);
//            ResultSetMetaData md = connection.getResult().getMetaData();
//            for (int i = 1; i <= md.getColumnCount(); i++) {
//                System.out.println("COLUMN " + i + " -> label: " + md.getColumnLabel(i) +" value: " + connection.extractString(md.getColumnLabel(i)));
//            }
            return mapResultSetToEstates();
        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
        }

        return estates;

    }

    @Override
    public Estate getEstateById(Integer idImmobile) throws DietiEstateException {

        String sql = """
          SELECT i.*, a.*, ag.*
          FROM immobile i
          JOIN agenziaimmobiliare ag ON i.partitaiva = ag.partitaiva
          JOIN agenteimmobiliare a ON i.idagente = a.idagente
          WHERE i.idimmobile = ?
        """;

        PreparedStatement ps = connection.getStatment(sql);
        try {
            ps.setInt(1, idImmobile);
            connection.makeQuery(ps);
            ResultSetMetaData md = connection.getResult().getMetaData();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                System.out.println("COLUMN " + i + " -> label: " + md.getColumnLabel(i));
            }

            if (!connection.hasNextRow()) throw new EstateNotExists();

            connection.nextRow();
            return mapCurrentRowToEstate();
        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            return null;
        }

    }

    private List<Estate> mapResultSetToEstates() throws SQLException, DietiEstateException {
        List<Estate> list = new ArrayList<>();
        do {
            connection.nextRow();
            list.add(mapCurrentRowToEstate());
        } while (connection.hasNextRow());
        return list;
    }

    private Estate mapCurrentRowToEstate() throws SQLException, DietiEstateException {

        Agency fullAgency = new Agency.Builder<>(
                connection.extractString("ag_partitaiva"))
                .setNome(connection.extractString("ag_nome"))
                .setSede(connection.extractString("ag_sede"))
                .build();
        System.out.println("Agency Success");

        Agent agente = new Agent.Builder(
                connection.extractInt("a_idagente"),
                connection.extractString("a_email"))
                .setName(connection.extractString("a_nome"))
                .setCognome(connection.extractString("a_cognome"))
                .setBiografia(connection.extractString("a_biografia"))
                .setProfilePic(connection.extractString("a_immagineprofilo"))
                .setIdPushNotify(connection.extractString("a_idpushnotify"))
                .setNotifyAppointment(connection.extractBoolean("a_notify_appointment"))
                .setAgency(fullAgency)
                .build();
        System.out.println("Agent Success");


        int idImmobile    = connection.extractInt("i_idimmobile");
        String descr      = connection.extractString("i_descrizione");
        double price      = connection.extractInt("i_prezzo");
        double space      = connection.extractInt("i_dimensioni");
        int rooms         = connection.extractInt("i_stanze");
        int floor         = connection.extractInt("i_piano");
        int wc            = connection.extractInt("i_bagni");
        int garage        = connection.extractInt("i_garage");
        boolean elevator  = connection.extractBoolean("i_ascensore");
        String classeStr  = connection.extractString("i_classeenergetica");
        String modeStr    = connection.extractString("i_modalita");
        String statusStr  = connection.extractString("i_stato");
        int idIndirizzo   = connection.extractInt("i_idindirizzo");

        System.out.println("Fetch Estate Success: " + classeStr + modeStr + statusStr);

        // 4. Converto stringhe di enum
        EnergeticClass classe = ConverterEnergeticClass.traslateFromString(classeStr);
        System.out.println("Extract Energetic Class Success");
        Mode mode             = ConverterMode.traslateFromString(modeStr);
        System.out.println("Extract Mode Success");
        Status status         = ConverterStatus.traslateFromString(statusStr);
        System.out.println("Extract Status Success");

        System.out.println("Fetch Enum Success");

        // 5. Carico l'indirizzo associato
        Indirizzo indirizzo   = new IndirizzoPostgreDAO().getIndirizzoFromId(idIndirizzo);

        // inserire il fetch delle immagini con una nuova query

        // 6. Costruisco l'Estate via Builder
        Estate estate = new Estate.Builder<>(idImmobile)
                .setAgenteBuilder(agente)
                .setAgenziaBuilder(fullAgency)
                .setDescrizioneBuilder(descr)
                .setPriceBuilder(price)
                .setSpaceBuilder(space)
                .setRoomsBuilder(rooms)
                .setFloorBuilder(floor)
                .setWcBuilder(wc)
                .setGarageBuilder(garage)
                .setElevatorBuilder(elevator)
                .setClasseEnergeticaBuilder(classe)
                .setModeBuilder(mode)
                .setStatoBuilder(status)
                .setIndirizzoBuilder(indirizzo)
                .build();

        return estate;
    }


    @Override
    public Agent getAgent(Estate estate) throws DietiEstateException{

        String query = "SELECT a.idagente,email,nome,cognome,biografia,immagineprofilo, a.partitaiva, notify_appointment " +
                "FROM (agenteimmobiliare as a join immobile ON immobile.idagente = a.idagente) " +
                "where immobile.idimmobile = ?";

        try{

            PreparedStatement stmt = connection.getStatment(query);
            stmt.setInt(1,estate.getIdEstate());
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()) throw new EstateNotExists();

            connection.nextRow();

            Agency agency = new Agency.Builder<>(connection.extractString("partitaiva"))
                    .build();

            return new Agent.Builder(connection.extractInt("idagente"),connection.extractString("email"))
                    .setName(connection.extractString("nome"))
                    .setCognome(connection.extractString("cognome"))
                    .setBiografia(connection.extractString("biografia"))
                    .setProfilePic(connection.extractString("immagineprofilo"))
                    .setAgency(agency)
                    .setNotifyAppointment(connection.extractBoolean("notify_appointment"))
                    .build();


        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
        }

        return null;
    }

    @Override
    public void updateEstateAgent(Estate estate, Agent agent) throws DietiEstateException {

        PreparedStatement stmt;
        String query;

        query= "UPDATE immobile SET idagente = ? WHERE idimmobile = ? ";
        
        try {
            stmt = connection.getStatment(query);
            stmt.setInt(1, agent.getIdUser());
            stmt.setInt(2, estate.getIdEstate());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        makeEstateUpdate(stmt);

    }


    private int makeEstateUpdate(PreparedStatement stmt) throws ErrorExecutingQuery {
        int res = 0;
        try {
            res = connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
        return res;
    }

    @Override
    public boolean isEstatePresent(Estate estate) throws DietiEstateException {
        String query="SELECT * FROM immobile where idimmobile = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.getStatment(query);

            stmt.setInt(1, estate.getIdEstate());
        } catch (Exception e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new EstateNotExists();
            return true;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    @Override
    public List<Estate> estatesSerachFromCity(Indirizzo indirizzo) throws DietiEstateException {
        String query= "SELECT * FROM immobile INNER JOIN indirizzo ON immobile.idindirizzo = indirizzo.idindirizzo" +
                " WHERE citta = ? OR quartiere = ?";

        PreparedStatement stmt;
        try {
            stmt = connection.getStatment(query);

            stmt.setString(1, indirizzo.getCitta());
            stmt.setString(2, indirizzo.getCitta());
        } catch (Exception e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new EstateNotExists();

            ArrayList<Estate> estates = new ArrayList<>();

            buildEstates(estates);

            return estates;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    private void buildEstates(List<Estate> estates) throws DietiEstateException {
        try {
        do{
            connection.nextRow();

            Indirizzo fulIndirizzo = new Indirizzo.Builder<>(connection.extractInt("idindirizzo"))
                    .setStato(connection.extractString("stato"))
                    .setCitta(connection.extractString("citta"))
                    .setVia(connection.extractString("via"))
                    .setNumeroCivico(connection.extractString("numerocivico"))
                    .setCap(connection.extractInt("cap"))
                    .setQuartiere(connection.extractString("quartiere"))
                    .build();

            Estate estate = new Estate.Builder<>(connection.extractInt("idimmobile"))
                    .setIndirizzoBuilder(fulIndirizzo)
                    .setElevatorBuilder(connection.extractBoolean("ascensore"))
                    .setAgenteBuilder(null)
                    .setClasseEnergeticaBuilder(null)
                    .setDescrizioneBuilder(connection.extractString("descrizione"))
                    .setFloorBuilder(connection.extractInt("piano"))
                    .setGarageBuilder(connection.extractInt("garage"))
                    .setModeBuilder(null)
                    .setRoomsBuilder(connection.extractInt("stanze"))
                    .setPriceBuilder(connection.extractInt("prezzo"))
                    .setWcBuilder(connection.extractInt("bagni"))
                    .setSpaceBuilder(connection.extractInt("dimensioni"))
                    .setStatoBuilder(null)
                    .setAgenziaBuilder(null)
                    .build();

            EnergeticClass classe = ConverterEnergeticClass.traslateFromString(connection.extractString("classeenergetica"));
            Mode mode = ConverterMode.traslateFromString(connection.extractString("modalita"));
            Status status = ConverterStatus.traslateFromString(connection.extractString("stato"));



            Agent agent = new Agent.Builder(connection.extractInt("idagente"),"").build();
            agent = agentDAO.getAgentFromId(agent);

            estate.setAgenzia(agent.getAgency());
            estate.setAgente(agent);
            estate.setClasseEnergetica(classe);
            estate.setMode(mode);
            estate.setStato(status);



            estates.add(estate);
        }while(connection.hasNextRow());

        for (Estate elem : estates) {
            elem.setFoto(takeFoto(elem.getIdEstate()));
        }

        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    private List<String> takeFoto(int idEstate) throws DietiEstateException{
        String query= "SELECT * FROM (immobile INNER JOIN fotoimmobile ON immobile.idimmobile = fotoimmobile.idimmobile) WHERE immobile.idimmobile = ?";
        PreparedStatement stmt = connection.getStatment(query);
        ArrayList<String> foto = new ArrayList<>();
        try {
            stmt.setInt(1, idEstate);
            connection.makeQuery(stmt);
            while(connection.hasNextRow()){
                connection.nextRow();
                String elem = connection.extractString("foto");

                foto.add(elem);
            }
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }


        return foto;
    }

    @Override
    public void close(){
        if(connection != null)connection.close();
        agentDAO.close();
    }

    @Override
    public List<Estate> search(EstateFilter filter) throws DietiEstateException {
        PreparedStatement stmt = generateStmtSearch(filter);

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new EstateNotExists();
            ArrayList<Estate> estates = new ArrayList<>();

            buildEstates(estates);
            return estates;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    private boolean checkNullOrEmptyString(String string){
        return string != null && !string.isEmpty();
    }

    private PreparedStatement generateStmtSearch(EstateFilter filter) throws ErrorCreateStatment {
        PreparedStatement stmt;
        numberParamsOfFilter = 1;
        Map<Integer,String> presenzeString = new HashMap<>();
        Map<Integer,Integer> presenzeInteger = new HashMap<>();
        Map<Integer,Double> presenzeDouble = new HashMap<>();
        Map<Integer,Boolean> presenzeBoolean = new HashMap<>();
        Map<Integer,Object> presenzeObject = new HashMap<>();

        String query= "SELECT * FROM (immobile INNER JOIN indirizzo ON immobile.idindirizzo = indirizzo.idindirizzo) ";


        query = genereteQuerySringFilter(filter, query, presenzeString);

        query = genereteQueryIntegerFilter(filter, query, presenzeInteger);

        query = genereteQueryDoubleFilter(filter, query, presenzeDouble);

        query = genereteQueryBooleanFilter(filter, query, presenzeBoolean);

        if(checkNullOrEmptyString(filter.getMode())){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.modalita = ?::Modalita " : " AND immobile.modalita = ?::Modalita ";
            presenzeObject.put(numberParamsOfFilter, filter.getMode());
            numberParamsOfFilter++;

        }


        query += " ORDER BY " + filter.getSort() +" " + (Boolean.TRUE.equals(filter.getDesc()) ? "DESC" : "ASC") + " OFFSET ? LIMIT ? ";


        presenzeInteger.put(numberParamsOfFilter++, filter.getPage() - 1);
        presenzeInteger.put(numberParamsOfFilter, filter.getLimit());

        try {
            stmt = connection.getStatment(query);

            for (Map.Entry<Integer,String> entry : presenzeString.entrySet()) {
                stmt.setString(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<Integer,Integer> entry : presenzeInteger.entrySet()) {
                stmt.setInt(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<Integer,Boolean> entry : presenzeBoolean.entrySet()) {
                stmt.setBoolean(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<Integer,Double> entry : presenzeDouble.entrySet()) {
                stmt.setDouble(entry.getKey(), entry.getValue());
            }

            for (Map.Entry<Integer,Object> entry : presenzeObject.entrySet()) {
                stmt.setObject(entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;
    }

    private String genereteQueryBooleanFilter(EstateFilter filter, String query, Map<Integer, Boolean> presenzeBoolean) {
        if(filter.getElevator() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.ascensore = ? " : " AND immobile.ascensore = ? ";
            presenzeBoolean.put(numberParamsOfFilter, filter.getElevator());
            numberParamsOfFilter++;

        }
        return query;
    }

    private String genereteQueryDoubleFilter(EstateFilter filter, String query, Map<Integer, Double> presenzeDouble) {
        if(filter.getMinPrice() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.prezzo >= ? " : " AND immobile.prezzo >= ? ";
            presenzeDouble.put(numberParamsOfFilter, filter.getMinPrice());
            numberParamsOfFilter++;

        }

        if(filter.getMaxPrice() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.prezzo <= ? " : " AND immobile.prezzo <= ? ";
            presenzeDouble.put(numberParamsOfFilter, filter.getMaxPrice());
            numberParamsOfFilter++;
        }

        if(filter.getMinSpace() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.dimensione >= ? " : " AND immobile.dimensione >= ? ";
            presenzeDouble.put(numberParamsOfFilter, filter.getMinSpace());
            numberParamsOfFilter++;

        }

        if(filter.getMaxSpace() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.dimensione <= ? " : " AND immobile.dimensione <= ? ";
            presenzeDouble.put(numberParamsOfFilter, filter.getMinSpace());
            numberParamsOfFilter++;
        }
        return query;
    }

    private String genereteQueryIntegerFilter(EstateFilter filter, String query, Map<Integer, Integer> presenzeInteger) {
        if(filter.getMinRooms() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.stanze >= ? " : " AND immobile.stanze >= ? ";
            presenzeInteger.put(numberParamsOfFilter, filter.getMinRooms());
            numberParamsOfFilter++;
        }

        if(filter.getMaxRooms() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.stanze <= ? " : " AND immobile.stanze <= ? ";
            presenzeInteger.put(numberParamsOfFilter, filter.getMaxRooms());
            numberParamsOfFilter++;
        }

        if(filter.getWc() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.wc = ? " : " AND immobile.wc = ? ";
            presenzeInteger.put(numberParamsOfFilter, filter.getWc());
            numberParamsOfFilter++;
        }

        if(filter.getGarage() != null){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.garage = ? " : " AND immobile.garage = ? ";
            presenzeInteger.put(numberParamsOfFilter, filter.getGarage());
            numberParamsOfFilter++;

        }
        return query;
    }

    private String genereteQuerySringFilter(EstateFilter filter, String query, Map<Integer, String> presenzeString) {

        query = genereteQueryAddressSStringFilter(filter, query, presenzeString);

        if(checkNullOrEmptyString(filter.getState())){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.stato LIKE ? " : " AND immobile.stato LIKE ? ";
            presenzeString.put(numberParamsOfFilter, filter.getState());
            numberParamsOfFilter++;

        }

        if(checkNullOrEmptyString(filter.getEnergeticClass())){
            query += (numberParamsOfFilter == 1) ? " WHERE immobile.classeenergetica LIKE ? " : " AND immobile.classeenergetica LIKE ? ";
            presenzeString.put(numberParamsOfFilter, filter.getEnergeticClass());
            numberParamsOfFilter++;

        }



        return query;
    }

    private String genereteQueryAddressSStringFilter(EstateFilter filter, String query, Map<Integer, String> presenzeString) {
        if(checkNullOrEmptyString(filter.getStato())){
            query += " WHERE indirizzo.stato LIKE ? ";
            presenzeString.put(numberParamsOfFilter, filter.getStato());
            numberParamsOfFilter++;
        }

        if(checkNullOrEmptyString(filter.getCitta())){
            query += (numberParamsOfFilter == 1) ? " WHERE citta LIKE ? " : " AND citta LIKE ? ";
            presenzeString.put(numberParamsOfFilter, filter.getCitta());
            numberParamsOfFilter++;

        }

        if(checkNullOrEmptyString(filter.getQuartiere())){
            query += (numberParamsOfFilter == 1) ? " WHERE quartiere LIKE ? " : " AND quartiere LIKE ? ";
            presenzeString.put(numberParamsOfFilter, filter.getQuartiere());
            numberParamsOfFilter++;

        }

        if(checkNullOrEmptyString(filter.getVia())){
            query += (numberParamsOfFilter == 1) ? " WHERE via LIKE ? " : " AND via LIKE ? ";
            presenzeString.put(numberParamsOfFilter, filter.getVia());
            numberParamsOfFilter++;

        }
        return query;
    }


}