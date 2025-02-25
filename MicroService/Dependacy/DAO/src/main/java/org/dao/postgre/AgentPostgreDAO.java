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

    private final String TABLE = "agenteimmobiliare";

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public AgentPostgreDAO() {
    }

    @Override
    public Agent getUser(Agent agent) throws DietiEstateException {

        String Query="SELECT * FROM agenteimmobiliare where ? = email";

        PreparedStatement stmt = PrepareStatementGetUser(agent, Query);

        try {
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
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
    public void createUser(Agent agent) throws DietiEstateException{
        String Query=
                "INSERT INTO agenteimmobiliare(email, biografia, nome, cognome, password, partitaiva, immagineprofilo, idpushnotify, notify_appointment) VALUES" +
                        "(?,?,?,?,crypt(?,'" +
                        connection.getKeyCrypt() +
                        "'),?,?,?,?)";

        System.out.println(Query);
        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, agent.getEmail());
            stmt.setString(2, agent.getBiografia());
            stmt.setString(3, agent.getNome());
            stmt.setString(4, agent.getCognome());
            stmt.setString(5, agent.getPassword());
            stmt.setString(6, agent.getAgency().getCodice_partitaIVA());
            stmt.setString(7, agent.getProfilePic());
            stmt.setString(8, agent.getIdPushNotify());
            stmt.setBoolean(9, agent.getNotify_appointment());
            System.out.println(stmt.toString());
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }


        try {
            connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
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
        String Query= "DELETE FROM agenteimmobiliare WHERE email = ?";
        System.out.println(Query);
        PreparedStatement stmt = connection.getStatment(Query);
        System.out.println(Query);
        try {
            stmt.setString(1, agent.getEmail());
            System.out.println(stmt.toString());
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        int res = 0;
        try {
            res = connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        if(res == 0){
            throw new UserNotExists();
        }
    }

}
