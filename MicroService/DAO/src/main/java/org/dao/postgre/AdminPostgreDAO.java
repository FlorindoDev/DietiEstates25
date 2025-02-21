package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AdminDAO;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.md.Utente.Admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AdminPostgreDAO extends UtentePostgreDAO implements AdminDAO {

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public AdminPostgreDAO() {}

    @Override
    public Admin getUser(Admin admin) throws DietiEstateException {

        String Query="SELECT * FROM amministratore where ? = email";

        PreparedStatement stmt = PrepareStatementGetUser(admin, Query);

        try {
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        //TODO MANCACO DEGLI ATTRIBUTI si devo aggiugnere prima nel DB
        admin = new Admin.Builder(connection.extractInt("idamministratore"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setIsSupport(connection.extractBoolean("IsSupportoAmministratore"))
                .build();

        return admin;
    }

    @Override
    public void createUser(Admin admin) {

    }

    @Override
    public void updateUser(Admin changes) {

    }
}
