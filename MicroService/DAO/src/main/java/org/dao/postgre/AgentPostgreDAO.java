package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AgentDAO;
import org.exc.DietiEstateException;
import org.exc.ErrorExecutingQuery;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

import java.util.logging.Logger;

public class AgentPostgreDAO extends UtentePostgreDAO implements AgentDAO {

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public AgentPostgreDAO() {
    }

    @Override
    public Agent getUser(Agent agent) throws DietiEstateException {

        String Query="SELECT * FROM agenteimmobiliare where ? = email";

        PrepareStatmentAndContactDB(agent, Query);

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
