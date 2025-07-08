package org.dao.postgre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.dao.Interfacce.AcquirenteDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

import DBLib.Postgres.CommunicationWithPostgre;

public class AcquirentePostgreDAO extends UtentePostgreDAO implements AcquirenteDAO {

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private static final String TABLE = "acquirente";
    private static final String ID_DB_FIELD = "idacquirente";

    private static final Logger logger = Logger.getLogger(AcquirentePostgreDAO.class.getName());

    public AcquirentePostgreDAO() {
        super();
    }

    public AcquirentePostgreDAO(CommunicationWithPostgre connection) {
       super(connection);
    }

    @Override
    public Acquirente getUser(Acquirente acquirente) throws DietiEstateException {

        String query="SELECT * FROM acquirente where ? = email";

        PreparedStatement stmt = prepareStatementGetUser(acquirente, query);

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new UserNotExists("Acquirente non trovato");
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        return new Acquirente.Builder(connection.extractInt(ID_DB_FIELD), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setIdPushNotify(connection.extractString("idPushNotify"))
                .setChangePriceNotify(connection.extractBoolean("change_price_notify"))
                .setNotifyNewEstate(connection.extractBoolean("notify_new_estate"))
                .setNotifyAppointment(connection.extractBoolean("notify_appointment"))
                .build();
    }

    @Override
    public void createUser(Acquirente acquirente) throws DietiEstateException {
        String query = "INSERT INTO acquirente(Email, Nome, Cognome, Password, notify_appointment, notify_new_estate, change_price_notify) " +
                "VALUES (?, ?, ?, crypt(?, '" + connection.getKeyCrypt() + "'), ?, ?, ?)";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, acquirente.getEmail());
            stmt.setString(2, acquirente.getNome());
            stmt.setString(3, acquirente.getCognome());
            stmt.setString(4, acquirente.getPassword());
            stmt.setBoolean(5, false); // notify_appointment
            stmt.setBoolean(6, false); // notify_new_estate
            stmt.setBoolean(7, false); // change_price_notify
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

        super.updateUser(utente, TABLE, ID_DB_FIELD);

    }

    @Override
    public boolean isUserAbsent(Acquirente user) throws DietiEstateException {

        return super.isUserAbsent(user, TABLE);

    }

    @Override
    public boolean isUserPresent(Acquirente user) throws DietiEstateException {

        return super.isUserPresent(user, TABLE);

    }

    @Override
    public boolean isUserAbsentOverAll(Acquirente acquirente) throws DietiEstateException {
        return super.isUserAbsentOverAll(acquirente);
    }


}
