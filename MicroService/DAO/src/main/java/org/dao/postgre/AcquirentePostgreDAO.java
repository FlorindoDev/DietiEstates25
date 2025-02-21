package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserAlreadyExists;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AcquirentePostgreDAO extends UtentePostgreDAO implements AcquirenteDAO {
    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public AcquirentePostgreDAO() {}

    @Override
    public Acquirente getUser(Acquirente acquirente) throws DietiEstateException {

        String Query="SELECT * FROM acquirente where ? = email";

        PreparedStatement stmt = PrepareStatementGetUser(acquirente, Query);

        try {
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        acquirente = new Acquirente.Builder(connection.extractInt("idacquirente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setIdPushNotify(connection.extractString("idPushNoitfy"))
                .setChangePriceNotify(connection.extractBoolean("notify_price_notify"))
                .setNotifyNewEstate(connection.extractBoolean("notify_new_estate"))
                .setNotifyAppointment(connection.extractBoolean("notify_appointment"))
                .build();

        return acquirente;
    }

    @Override
    public void createUser(Acquirente acquirente) throws DietiEstateException {


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
    public void updateUser(Acquirente changes) throws DietiEstateException {

        JSONObject jsonObject = new JSONObject(changes.Translate());
        if (!jsonObject.isEmpty()) { // per evitare di fare update a vuoto

            StringBuilder query = new StringBuilder("UPDATE acquirente SET ");
            List<Object> parameters = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);

                if (!"id_user".equals(key) && !"email".equals(key) && !"null".equals(value.toString())) {
                    query.append(key).append(" = ?, ");
                    parameters.add(value);
                }

            }

            query.setLength(query.length() - 2);

            query.append(" where email = ?");

            PreparedStatement stmt = connection.getStatment(String.valueOf(query));
            try {
                int index = 1;
                for (Object param : parameters) {
                    stmt.setObject(index++, param); //parte da 1
                }

                stmt.setString(index, changes.getEmail());
                System.out.println(stmt.toString());

                connection.makeQueryUpdate(stmt);
            }catch (SQLException e){
                logger.severe("[-] Error executing query: " + e.getMessage());
                throw new ErrorExecutingQuery();
            }
        }

    }

    @Override
    public boolean isUserAbsent(Acquirente acquirente) throws DietiEstateException {

        String Query="SELECT * FROM acquirente where email = ?";

        //lancia eccezzione se non trova utente
        PreparedStatement stmt = PrepareStatementGetUser(acquirente, Query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) throw new UserAlreadyExists();
            return true;
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public boolean isUserPresent(Acquirente acquirente) throws DietiEstateException {

        String Query="SELECT * FROM acquirente where email = ?";

        //lancia eccezzione se non trova utente
        PreparedStatement stmt = PrepareStatementGetUser(acquirente, Query);

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new UserNotExists();
            return true;
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }
}
