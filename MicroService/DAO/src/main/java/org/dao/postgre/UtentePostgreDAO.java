package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.UtenteDAO;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UtentePostgreDAO implements UtenteDAO {

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    @Override
    public Utente getUser(Utente utente) {

        String Query="SELECT * FROM acquirente where ? = idacquirente";

        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setInt(1,utente.getId_user());
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new RuntimeException(e);
        }

        connection.makeQuery(stmt);

        connection.hasNextRow();

        Utente user = new Utente.Builder(connection.extractInt("idacquirente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .build();

        return user;
    }

    @Override
    public void createUser(Utente user) {

    }

    @Override
    public void updateUser(Utente changes) {

    }

    @Override
    public Admin getUser(Admin admin) {
        return null;
    }

    @Override
    public Agent getUser(Agent agent) {
        return null;
    }

    @Override
    public void createUser(Admin admin) {

    }

    @Override
    public void createUser(Agent agent) {

    }

    @Override
    public void updateUser(Admin changes) {

    }

    @Override
    public void updateUser(Agent changes) {

    }
}
