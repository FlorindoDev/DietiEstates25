package org.dao.postgre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.dao.Interfacce.AgentDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Utente.Agent;

import DBLib.Postgres.CommunicationWithPostgre;

public class AgentPostgreDAO extends UtentePostgreDAO implements AgentDAO {

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private static final String TABLE = "agenteimmobiliare";
    private static final String ID_DB_FIELD = "idagente";


    private static final Logger logger = Logger.getLogger(AgentPostgreDAO.class.getName());

    public AgentPostgreDAO() {
        super();
    }

    public AgentPostgreDAO(CommunicationWithPostgre connection) {
        super(connection);
    }

    @Override
    public Agent getUser(Agent agent) throws DietiEstateException {

        String query="SELECT * FROM agenteimmobiliare INNER JOIN agenziaimmobiliare ON agenteimmobiliare.partitaiva = agenziaimmobiliare.partitaiva where ? = email";

        PreparedStatement stmt = prepareStatementGetUser(agent, query);

        try {
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }


        Agency agency = buildAgencyFromConnection();

        agent = buildAgentFromConnection(agency);

        return agent;
    }

    private Agency buildAgencyFromConnection() {
        return new Agency.Builder<>(connection.extractString("partitaiva"))
                .setNome(connection.extractString("nome"))
                .setSede(connection.extractString("sede"))
                .build();

    }

    public Agent getAgentFromId(Agent agent) throws DietiEstateException {

        String query="SELECT * FROM agenteimmobiliare INNER JOIN agenziaimmobiliare ON agenteimmobiliare.partitaiva = agenziaimmobiliare.partitaiva where idagente = ?";

        PreparedStatement stmt = connection.getStatment(query);
        try {
            stmt.setInt(1, agent.getIdUser());
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        Agency agency = buildAgencyFromConnection();

        agent = buildAgentFromConnection(agency);

        return agent;
    }

    private Agent buildAgentFromConnection(Agency agency) {
        return new Agent.Builder(connection.extractInt(ID_DB_FIELD), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setBiografia(connection.extractString("biografia"))
                .setProfilePic(connection.extractString("ImmagineProfilo"))
                .setIdPushNotify(connection.extractString("idPushNoitfy"))
                .setNotifyAppointment(connection.extractBoolean("notify_appointment"))
                .setAgency(agency)
                .build();
    }

    @Override
    public Agent estraiIdFromEmailAgent(Agent agent) throws ErrorCreateStatment {
        String query= "SELECT * FROM agenteimmobiliare WHERE email = ?";

        PreparedStatement stmt;

        try {
            stmt = connection.getStatment(query);
            stmt.setString(1, agent.getEmail());
            connection.makeQuery(stmt);
            connection.nextRow();
            agent.setIdUser(connection.extractInt("idagente"));
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }
        return agent;
    }


    @Override
    public void createUser(Agent agent) throws DietiEstateException{
        String query=
                "INSERT INTO agenteimmobiliare(email, biografia, nome, cognome, password, partitaiva, immagineprofilo, idpushnotify, notify_appointment) VALUES" +
                        "(?,?,?,?,crypt(?,'" +
                        connection.getKeyCrypt() +
                        "'),?,?,?,?)";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, agent.getEmail());
            stmt.setString(2, agent.getBiografia());
            stmt.setString(3, agent.getNome());
            stmt.setString(4, agent.getCognome());
            stmt.setString(5, agent.getPassword());
            stmt.setString(6, agent.getAgency().getCodicePartitaIVA());
            stmt.setString(7, agent.getProfilePic());
            stmt.setString(8, agent.getIdPushNotify());
            stmt.setBoolean(9, agent.getNotifyAppointment());
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
    public void updateUser(Agent utente) throws DietiEstateException{
        super.updateUser(utente, TABLE, ID_DB_FIELD);
    }

    @Override
    public boolean isUserAbsent(Agent user) throws DietiEstateException {

        return super.isUserAbsent(user, TABLE);

    }

    public boolean isUserAbsentOverAll(Agent user) throws DietiEstateException {

        return super.isUserAbsentOverAll(user);

    }

    @Override
    public boolean isUserPresent(Agent user) throws DietiEstateException {

        return super.isUserPresent(user, TABLE);

    }

    @Override
    public List<Integer> getIdEstate(Agent agent) throws DietiEstateException {

        String query= "SELECT idimmobile FROM immobile JOIN agenteimmobiliare ON agenteimmobiliare.idagente = immobile.idagente where agenteimmobiliare.email = ?";

        ArrayList<Integer> idEtsates = new ArrayList<>();

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, agent.getEmail());
            connection.makeQuery(stmt);
            while(connection.hasNextRow()){
                connection.nextRow();
                idEtsates.add(connection.extractInt("idimmobile"));
            }
            return idEtsates;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }


    }

    @Override
    public void removeAdmin(Agent agent) throws DietiEstateException {
        String query= "DELETE FROM agenteimmobiliare WHERE email = ?";
        PreparedStatement stmt = connection.getStatment(query);
        try {
            stmt.setString(1, agent.getEmail());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        int res = 0;
        try {
            res = connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        if(res == 0){
            throw new UserNotExists();
        }
    }

}
