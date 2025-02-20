package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserElreadyExists;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AcquirentePostgreDAO extends UtentePostgreDAO implements AcquirenteDAO {
    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public AcquirentePostgreDAO() {}

    @Override
    public Acquirente getUser(Acquirente acquirente) throws DietiEstateException {

        String Query="SELECT * FROM acquirente where ? = email";

        PreparedStatement stmt = PrepareStatementGetUser(acquirente, Query);

        ContactDB(stmt);

        //TODO MANCACO DEGLI ATTRIBUTI, si devo aggiugnere prima nel DB
        acquirente = new Acquirente.Builder(connection.extractInt("idagente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .build();

        return acquirente;
    }

    @Override
    public void createUser(Acquirente acquirente) throws DietiEstateException {

        //TODO MANCACO DEGLI ATTRIBUTI, si devo aggiugnere prima nel DB

        String Query="INSERT INTO acquirente(Email, Nome, Cognome, Password) VALUES(?,?,?,crypt( ? , '" +  connection.getKeyCrypt() +"'))";

        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, acquirente.getEmail());
            stmt.setString(2, acquirente.getNome());
            stmt.setString(3, acquirente.getCognome());
            stmt.setString(4, acquirente.getPassword());
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
    public void updateUser(Acquirente changes) {

    }

    @Override
    public boolean isUserAbsent(Acquirente acquirente) throws DietiEstateException {

        String Query="SELECT * FROM acquirente where email = ?";

        //lancia eccezzione se non trova utente
        PreparedStatement stmt = PrepareStatementGetUser(acquirente, Query);

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new UserElreadyExists();
            return true;
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }



    }
}
