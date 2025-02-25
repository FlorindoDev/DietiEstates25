package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AcquirentePostgreDAO extends UtentePostgreDAO implements AcquirenteDAO {

    public static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private final String table = "acquirente";

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(AcquirentePostgreDAO.class.getName());

    public AcquirentePostgreDAO() {
        //Serve per il framework di JAX-RS
    }

    @Override
    public Acquirente getUser(Acquirente acquirente) throws DietiEstateException {

        String query="SELECT * FROM acquirente where ? = email";

        PreparedStatement stmt = prepareStatementGetUser(acquirente, query);

        try {
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        acquirente = new Acquirente.Builder(connection.extractInt("idacquirente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setIdPushNotify(connection.extractString("idPushNotify"))
                .setChangePriceNotify(connection.extractBoolean("change_price_notify"))
                .setNotifyNewEstate(connection.extractBoolean("notify_new_estate"))
                .setNotifyAppointment(connection.extractBoolean("notify_appointment"))
                .build();

        return acquirente;
    }

    @Override
    public void createUser(Acquirente acquirente) throws DietiEstateException {


        String query="INSERT INTO acquirente(Email, Nome, Cognome, Password) VALUES(?,?,?,crypt( ? , '" +  connection.getKeyCrypt() +"'))";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, acquirente.getEmail());
            stmt.setString(2, acquirente.getNome());
            stmt.setString(3, acquirente.getCognome());
            stmt.setString(4, acquirente.getPassword());
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
    public void updateUser(Acquirente utente) throws DietiEstateException {

        super.updateUser(utente, table);

    }

    @Override
    public boolean isUserAbsent(Acquirente user) throws DietiEstateException {

        return super.isUserAbsent(user, table);

    }

    @Override
    public boolean isUserPresent(Acquirente user) throws DietiEstateException {

        return super.isUserPresent(user, table);

    }

    @Override
    public boolean isUserAbsentOverAll(Acquirente acquirente) throws DietiEstateException {
        return super.isUserAbsentOverAll(acquirente);
    }
}
