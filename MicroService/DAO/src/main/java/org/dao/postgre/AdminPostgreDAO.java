package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AdminDAO;
import org.exc.ErrorExecutingQuery;
import org.md.Utente.Admin;
import org.md.Utente.Utente;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AdminPostgreDAO extends UtentePostgreDAO implements AdminDAO {

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public AdminPostgreDAO() {}

    @Override
    public Admin getUser(Admin admin) throws ErrorExecutingQuery {

        String Query="SELECT * FROM amministratore where ? = email";

        PrepareStatmentAndContactDB(admin, Query);

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
