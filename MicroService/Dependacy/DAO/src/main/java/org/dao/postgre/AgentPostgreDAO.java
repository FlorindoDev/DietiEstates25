package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AgentDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DietiEstateException;
import org.md.Utente.Agent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AgentPostgreDAO extends UtentePostgreDAO implements AgentDAO {

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private static final String TABLE = "agenteimmobiliare";

    private final CommunicationWithPostgre connection;
    private static final Logger logger = Logger.getLogger(AgentPostgreDAO.class.getName());

    public AgentPostgreDAO() {
        connection = new CommunicationWithPostgre();
    }

    @Override
    public Agent getUser(Agent agent) throws DietiEstateException {

        String query="SELECT * FROM agenteimmobiliare where ? = email";

        PreparedStatement stmt = prepareStatementGetUser(agent, query);

        try {
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }


        agent = new Agent.Builder(connection.extractInt("idagente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setBiografia(connection.extractString("biografia"))
                .setBiografia(connection.extractString("ImmagineProfilo"))
                .setIdPushNotify(connection.extractString("idPushNoitfy"))
                .setNotifyAppointment(connection.extractBoolean("notify_appointment"))
                .build();

        return agent;
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
        super.updateUser(utente, TABLE);
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
