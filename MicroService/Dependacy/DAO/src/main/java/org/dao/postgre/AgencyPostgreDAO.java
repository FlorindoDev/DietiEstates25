package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AdminDAO;
import org.dao.Interfacce.AgencyDAO;
import org.exc.DataBaseException.*;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.ClasseEnergetica.ConverterEnergeticClass;
import org.md.Estate.ClasseEnergetica.EnergeticClass;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Estate.Mode.ConverterMode;
import org.md.Estate.Mode.Mode;
import org.md.Estate.Status.ConverterStatus;
import org.md.Estate.Status.Status;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AgencyPostgreDAO implements AgencyDAO {

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private static final String COGNOME_COLUMN = "cognome";
    private static final String PARTITAIVA_COLUMN = "partitaiva";
    private static final String EMAIL_COLUMN = "email";
    private final CommunicationWithPostgre connection;
    private static final Logger logger = Logger.getLogger(AgencyPostgreDAO.class.getName());

    public AgencyPostgreDAO() {
        connection = new CommunicationWithPostgre();
    }

    protected PreparedStatement prepareStatementGetAgency(Agency agency, String query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, agency.getCodicePartitaIVA());
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;

    }

    protected PreparedStatement prepareStatementGetAgencyForName(Agency agency, String query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, agency.getNome());
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;

    }

    @Override
    public void createAgency(Agency agency) throws DietiEstateException {


        String query="INSERT INTO agenziaimmobiliare(PartitaIVA, Nome, Sede) VALUES(?,?,?)";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, agency.getCodicePartitaIVA());
            stmt.setString(2, agency.getNome());
            stmt.setString(3, agency.getSede());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    @Override
    public boolean isAgencyAbsent(Agency agency) throws DietiEstateException {

        String query = "SELECT * FROM agenziaimmobiliare where partitaIVA = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) throw new AgencyAlreadyExists();
            return true;
        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    public boolean isAgencyPresent(Agency agency) throws DietiEstateException {

        String query = "SELECT * FROM agenziaimmobiliare where partitaIVA = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new AgencyNotExists();
            return true;
        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    @Override
    public boolean isNameAgencyAbsent(Agency agency) throws DietiEstateException {

        String query = "SELECT * FROM agenziaimmobiliare where nome = ?";

        PreparedStatement stmt = prepareStatementGetAgencyForName(agency,query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) throw new AgencyNameAlreadyExists();
            return true;
        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public ArrayList<Admin> getAdmins(Agency agency) throws DietiEstateException {

        isAgencyPresent(agency);

        String query = "SELECT *, agenziaimmobiliare.nome AS nomeagenzia" +
                " FROM amministratore INNER JOIN agenziaimmobiliare ON amministratore.partitaiva = agenziaimmobiliare.partitaiva" +
                " WHERE amministratore.partitaiva = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);



        try {
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()){
                throw new AgencyAdminsNotExists();
            }
            ArrayList<Admin> admins = new ArrayList<>();

            do{
                connection.nextRow();

                Agency fullAgency = new Agency.Builder<>(connection.extractString(PARTITAIVA_COLUMN))
                        .setNome(connection.extractString("nomeagenzia"))
                        .setSede(connection.extractString("sede"))
                        .build();

                Admin admin = new Admin.Builder(1,connection.extractString(EMAIL_COLUMN))
                        .setName(connection.extractString("nome"))
                        .setCognome(connection.extractString(COGNOME_COLUMN))
                        .setIsSupport(connection.extractBoolean("issupportoamministratore"))
                        .setAgency(fullAgency)
                        .build();

                admins.add(admin);


            }while(connection.hasNextRow());

            return admins;

        }catch(Exception e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }



    }

    @Override
    public ArrayList<Agent> getAgents(Agency agency) throws DietiEstateException {
        isAgencyPresent(agency);

        String query = "SELECT *" +
                " FROM agenteimmobiliare INNER JOIN agenziaimmobiliare ON agenteimmobiliare.partitaiva = agenziaimmobiliare.partitaiva" +
                " WHERE agenteimmobiliare.partitaiva = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);



        try {
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()){
                throw new AgencyAgentsNotExists();
            }
            ArrayList<Agent> agents = new ArrayList<>();

            do{
                connection.nextRow();

                Agency fullAgency = new Agency.Builder<>(connection.extractString(PARTITAIVA_COLUMN))
                        .setNome(connection.extractString("nome"))
                        .setSede(connection.extractString("sede"))
                        .build();

                Agent agent = new Agent.Builder(1,connection.extractString(EMAIL_COLUMN))
                        .setName(connection.extractString("nome"))
                        .setCognome(connection.extractString(COGNOME_COLUMN))
                        .setAgency(fullAgency)
                        .build();

                agents.add(agent);


            }while(connection.hasNextRow());

            return agents;

        }catch(Exception e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }


    }

    @Override
    public ArrayList<Estate> getEstates(Agency agency) throws DietiEstateException {
        isAgencyPresent(agency);

        String query = "SELECT *" +
                " FROM (immobile INNER JOIN agenziaimmobiliare ON immobile.partitaiva = agenziaimmobiliare.partitaiva)" +
                "INNER JOIN agenteimmobiliare on agenteimmobiliare.idagente = immobile.idagente" +
                " WHERE immobile.partitaiva = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);



        try {
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()){
                throw new AgencyEstatesNotExists();
            }
            ArrayList<Estate> estates = new ArrayList<>();

            do{
                connection.nextRow();

                Agency fullAgency = new Agency.Builder<>(connection.extractString(PARTITAIVA_COLUMN))
                        .setNome(connection.extractString("nome"))
                        .setSede(connection.extractString("sede"))
                        .build();

                Agent agente = new Agent.Builder(connection.extractInt("idagente"),connection.extractString(EMAIL_COLUMN))
                        .setBiografia(connection.extractString("biografia"))
                        .setProfilePic(connection.extractString("immagineprofilo"))
                        .setName(connection.extractString("nome"))
                        .setCognome(connection.extractString(COGNOME_COLUMN))
                        .setIdPushNotify(connection.extractString("idpushnotify"))
                        .setNotifyAppointment(connection.extractBoolean("notify_appointment"))
                        .setAgency(fullAgency)
                        .build();


                Estate estate = new Estate.Builder<>(connection.extractInt("idimmobile"))
                        .setElevatorBuilder(connection.extractBoolean("ascensore"))
                        .setAgenteBuilder(agente)
                        .setClasseEnergeticaBuilder(null)
                        .setDescrizioneBuilder(connection.extractString("descrizione"))
                        .setFloorBuilder(connection.extractInt("piano"))
                        .setFotoBuilder(connection.extractString("foto"))
                        .setGarageBuilder(connection.extractInt("garage"))
                        .setModeBuilder(null)
                        .setIndirizzoBuilder(null)
                        .setRoomsBuilder(connection.extractInt("stanze"))
                        .setPriceBuilder(connection.extractInt("prezzo"))
                        .setWcBuilder(connection.extractInt("bagni"))
                        .setSpaceBuilder(connection.extractInt("dimensioni"))
                        .setStatoBuilder(null)
                        .setAgenziaBuilder(fullAgency)
                        .build();


                EnergeticClass classe = ConverterEnergeticClass.traslateFromString(connection.extractString("classeenergetica"));
                Mode mode = ConverterMode.traslateFromString(connection.extractString("modalita"));
                Status status = ConverterStatus.traslateFromString(connection.extractString("stato"));

                Indirizzo indirizzo = new IndirizzoPostgreDAO().getIndirizzoFromId(connection.extractInt("idindirizzo"));


                estate.setClasseEnergetica(classe);
                estate.setMode(mode);
                estate.setStato(status);
                estate.setIndirizzo(indirizzo);


                estates.add(estate);


            }while(connection.hasNextRow());

            return estates;

        }catch(Exception e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    public void createAgencyAtomic(Agency agency) throws DietiEstateException {

        try {

            connection.setAutoCommit(false);

            AdminDAO adminDAO = new AdminPostgreDAO(connection);

            Admin admin = agency.getAdmins().getFirst();

            isAgencyAbsent(agency);

            isNameAgencyAbsent(agency);

            createAgency(agency);

            adminDAO.isUserAbsentOverAll(admin);

            adminDAO.createUser(admin);

            connection.commitActions();

        } catch (DietiEstateException e) {
            connection.rollBackAction();
            logger.info("[!] rollback");
            throw e;

        }catch(Exception ex){
            logger.severe(ERROR_EXECUTING_QUERY + ex.getMessage());
            connection.rollBackAction();
            logger.info("[!] rollback");
            throw new ErrorExecutingQuery();
        }finally {
            connection.setAutoCommit(true);
        }

    }
    @Override
    public void close(){
        connection.close();
    }


}
