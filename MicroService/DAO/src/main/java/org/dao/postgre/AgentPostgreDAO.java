package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AgentDAO;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.md.Utente.Agent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AgentPostgreDAO extends UtentePostgreDAO implements AgentDAO {

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

        //TODO MANCACO DEGLI ATTRIBUTI si devo aggiugnere prima nel DB
        agent = new Agent.Builder(connection.extractInt("idagente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setBiografia(connection.extractString("biografia"))
                .setBiografia(connection.extractString("ImmagineProfilo"))
                .build();

        return agent;
    }

    @Override
    public void createUser(Agent changes) {

    }

    @Override
    public void updateUser(Agent changes) {

    }
}
