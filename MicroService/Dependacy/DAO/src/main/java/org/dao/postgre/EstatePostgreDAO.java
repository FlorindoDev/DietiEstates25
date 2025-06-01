package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AgentDAO;
import org.dao.Interfacce.EstateDAO;

import org.exc.DataBaseException.*;
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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.logging.Logger;

public class EstatePostgreDAO implements EstateDAO {

    private static final String TABLE = "immobile";
    protected static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";

    private CommunicationWithPostgre connection;

    private static final Logger logger = Logger.getLogger(EstatePostgreDAO.class.getName());

    private final AgentDAO agentDAO;

    public EstatePostgreDAO() {
        this.connection = new CommunicationWithPostgre();
        this.agentDAO = new AgentPostgreDAO();
    }

    @Override
    public void createEstate(Estate newEstate) throws DietiEstateException {

        String query = "INSERT INTO " + TABLE + " (" +
                "idagente, idindirizzo, partitaIVA, foto, descrizione, prezzo, dimensioni, stanze, piano, bagni, garage, ascensore, classeenergetica, modalita, stato) VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            idAddress = connection.extractInt("idindirizzo");
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

            connection.makeQueryUpdate(stmt);
            connection.commitActions();

        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
             connection.rollBackAction();
            throw new ErrorExecutingQuery();
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
        if (!estate.getFoto().isEmpty()) {
            query.append("foto = ?, ");
            params.add(estate.getFoto());
        }
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
                    .setCognome(connection.extractString("nome"))
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
            estate.setFoto(takeFoto(estate.getIdEstate()));


            estates.add(estate);
        }while(connection.hasNextRow());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    private List<String> takeFoto(int idEstate) throws DietiEstateException{
        String query= "SELECT * FROM (immobile INNER JOIN fotoimmobile ON immobile.idimmobile = fotoimmobile.idimmobile) ";
        PreparedStatement stmt = connection.getStatment(query);
        ArrayList<String> foto = new ArrayList<>();
        try {
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

    private PreparedStatement generateStmtSearch(EstateFilter filter) throws ErrorCreateStatment {
        PreparedStatement stmt;
        String query= "SELECT * FROM (immobile INNER JOIN indirizzo ON immobile.idindirizzo = indirizzo.idindirizzo) ";

        Map<Integer,String> presenzeString = new HashMap<>();
        Map<Integer,Integer> presenzeInteger = new HashMap<>();
        Map<Integer,Double> presenzeDouble = new HashMap<>();
        Map<Integer,Boolean> presenzeBoolean = new HashMap<>();
        int i = 1;
        boolean primo = true;

        if(filter.getStato() != null && !filter.getStato().isEmpty()){
            query += " WHERE indirizzo.stato LIKE ? ";
            presenzeString.put(i, filter.getStato());
            i++;
            primo = false;
        }

        if(filter.getCitta() != null && !filter.getCitta().isEmpty()){
            query += primo ? " WHERE citta LIKE ? " : " AND citta LIKE ? ";
            presenzeString.put(i, filter.getCitta());
            i++;
            primo = false;

        }

        if(filter.getQuartiere() != null && !filter.getQuartiere().isEmpty()){
            query += primo ? " WHERE quartiere LIKE ? " : " AND quartiere LIKE ? ";
            presenzeString.put(i, filter.getQuartiere());
            i++;
            primo = false;

        }

        if(filter.getVia() != null && !filter.getVia().isEmpty()){
            query += primo ? " WHERE via LIKE ? " : " AND via LIKE ? ";
            presenzeString.put(i, filter.getVia());
            i++;
            primo = false;

        }

        if(filter.getState() != null && !filter.getState().isEmpty()){
            query += primo ? " WHERE immobile.stato LIKE ? " : " AND immobile.stato LIKE ? ";
            presenzeString.put(i, filter.getState());
            i++;
            primo = false;
        }

        if(filter.getMinRooms() != null){
            query += primo ? " WHERE immobile.stanze >= ? " : " AND immobile.stanze >= ? ";
            presenzeInteger.put(i, filter.getMinRooms());
            i++;
            primo = false;
        }

        if(filter.getMaxRooms() != null){
            query += primo ? " WHERE immobile.stanze <= ? " : " AND immobile.stanze <= ? ";
            presenzeInteger.put(i, filter.getMaxRooms());
            i++;
            primo = false;
        }

        if(filter.getWc() != null){
            query += primo ? " WHERE immobile.wc = ? " : " AND immobile.wc = ? ";
            presenzeInteger.put(i, filter.getWc());
            i++;
            primo = false;
        }

        if(filter.getGarage() != null){
            query += primo ? " WHERE immobile.garage = ? " : " AND immobile.garage = ? ";
            presenzeInteger.put(i, filter.getGarage());
            i++;
            primo = false;
        }

        if(filter.getMinPrice() != null){
            query += primo ? " WHERE immobile.prezzo >= ? " : " AND immobile.prezzo >= ? ";
            presenzeDouble.put(i, filter.getMinPrice());
            i++;
            primo = false;
        }

        if(filter.getMaxPrice() != null){
            query += primo ? " WHERE immobile.prezzo <= ? " : " AND immobile.prezzo <= ? ";
            presenzeDouble.put(i, filter.getMaxPrice());
            i++;
            primo = false;
        }

        if(filter.getMinSpace() != null){
            query += primo ? " WHERE immobile.dimensione >= ? " : " AND immobile.dimensione >= ? ";
            presenzeDouble.put(i, filter.getMinSpace());
            i++;
            primo = false;
        }

        if(filter.getMaxSpace() != null){
            query += primo ? " WHERE immobile.dimensione <= ? " : " AND immobile.dimensione <= ? ";
            presenzeDouble.put(i, filter.getMinSpace());
            i++;
            primo = false;
        }

        if(filter.getElevator() != null){
            query += primo ? " WHERE immobile.ascensore = ? " : " AND immobile.ascensore = ? ";
            presenzeBoolean.put(i, filter.getElevator());
            i++;

        }


        query += " ORDER BY ? " + (Boolean.TRUE.equals(filter.getDesc())? "DESC" : "ASC") + " OFFSET ? LIMIT ? ";

        presenzeString.put(i++, filter.getSort());
        presenzeInteger.put(i++, filter.getPage() - 1);
        presenzeInteger.put(i, filter.getLimit());

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

        } catch (Exception e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;
    }


}