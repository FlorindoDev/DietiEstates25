package org.dao.postgre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.dao.Interfacce.UtenteDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserAlreadyExists;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DataBaseException.UserNotFoundException;
import org.exc.DietiEstateException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

import DBLib.Postgres.CommunicationWithPostgre;

public class UtentePostgreDAO implements UtenteDAO {

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private static final String ID_USER_COLUMN = "id_user";
    private static final String EMAIL_COLUMN = "email";
    private static final String PASSWORD_COLUMN = "password";
    protected final CommunicationWithPostgre connection;
    private static final Logger logger = Logger.getLogger(UtentePostgreDAO.class.getName());


    public UtentePostgreDAO(CommunicationWithPostgre connection) {
        this.connection = connection;
    }

    public UtentePostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    protected PreparedStatement prepareStatementGetUser(Utente utente, String query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, utente.getEmail());
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;

    }

    private PreparedStatement prepareStatementGetForLogin(Utente utente, String query) throws DietiEstateException {

        PreparedStatement stmt = connection.getStatment(query);

        try {

            int numeroDiParametriQuery=6;
            String email = utente.getEmail();
            String password = utente.getPassword();

            for(int i=1;i<numeroDiParametriQuery;i+=2){
                stmt.setString(i, email);
                stmt.setString(i+1, password);
            }

        } catch (Exception e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();

        }
        return stmt;
    }

    private Utente retrunEffectiveType(Utente user) {

        String type_user = connection.extractString("user_type");
        String Acquirente_type = Acquirente.class.getSimpleName();
        String Admin_type = Admin.class.getSimpleName();
        String Agent_type = Agent.class.getSimpleName();

        if(type_user.equals(Acquirente_type)){

            user = new Acquirente.Builder(connection.extractInt(ID_USER_COLUMN), connection.extractString(EMAIL_COLUMN))
                    .build();

        } else if (type_user.equals(Admin_type)) {

            user = new Admin.Builder(connection.extractInt(ID_USER_COLUMN), connection.extractString(EMAIL_COLUMN))
                    .build();

        } else if (type_user.equals(Agent_type)) {

            user = new Agent.Builder(connection.extractInt(ID_USER_COLUMN), connection.extractString(EMAIL_COLUMN))
                    .build();
        }
        return user;
    }

    @Override
    public Utente getUser(Utente utente) throws DietiEstateException {

        Utente user=null;
        String keycrypt = connection.getKeyCrypt();
        String query="SELECT idacquirente as id_user,email, 'Acquirente' AS user_type FROM acquirente WHERE email like ? and password like crypt( ? , '"+ keycrypt +"')" +
                " UNION " +
                "SELECT idamministratore as id_user,email, 'Admin' AS user_type FROM amministratore WHERE email like ? and password like crypt( ? , '"+ keycrypt +"')" +
                " UNION " +
                "SELECT idagente as id_user,email, 'Agent' AS user_type FROM agenteimmobiliare WHERE email like ? and password like crypt( ? , '"+ keycrypt +"')";


        PreparedStatement stmt = prepareStatementGetForLogin(utente, query);

        try {
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()) throw new UserNotFoundException();

            connection.nextRow();
            user = retrunEffectiveType(user);

        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        return user;
    }

    public Utente getUserFromEmail(String emailUser) throws DietiEstateException {

        Utente user=null;

        String query="SELECT idacquirente as id_user,email, 'Acquirente' AS user_type FROM acquirente WHERE email like ? " +
                " UNION " +
                "SELECT idamministratore as id_user,email, 'Admin' AS user_type FROM amministratore WHERE  email like ? " +
                " UNION " +
                "SELECT idagente as id_user,email, 'Agent' AS user_type FROM agenteimmobiliare WHERE email like ? ";

        try {
            PreparedStatement stmt = connection.getStatment(query);
            stmt.setString(1,emailUser);
            stmt.setString(2,emailUser);
            stmt.setString(3,emailUser);
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()) throw new UserNotExists();

            connection.nextRow();
            user = retrunEffectiveType(user);

        } catch (SQLException | NullPointerException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        return user;
    }


    public void updateUser(Utente changes, String tabella, String idFiled) throws DietiEstateException {

        final String ID_USER_COLUMN = "idUser";
        String keycrypt = connection.getKeyCrypt();

        Map<String, String> alias = Map.of(
                "notifyAppointment", "notify_appointment",
                "notifyNewEstate",   "notify_new_estate",
                "changePriceNotify", "change_price_notify",
                "support", "issupportoamministratore"
        );

        JSONObject jsonObject = new JSONObject(changes.TranslateToJson());

        if (!jsonObject.isEmpty()) { // per evitare di fare update a vuoto
            logger.info("JSON: " + jsonObject);
            StringBuilder query = new StringBuilder("UPDATE "+tabella+" SET ");
            List<Object> parameters = new ArrayList<>();

            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);

                // Salta tutte le mappe/oggetti JSON annidati
                if (value instanceof JSONObject || value instanceof JSONArray) continue;

                if (value == null || "null".equalsIgnoreCase(value.toString()) || "".equals(value.toString().trim())) continue;

                if (ID_USER_COLUMN.equals(key)) continue;

                if (PASSWORD_COLUMN.equals(key)){
                    if (value.toString().isBlank()) continue;
                    query.append(key).append(" = crypt( ? , '").append(keycrypt).append("'), ");
                    parameters.add(value);
                    continue;
                }

                String column = alias.getOrDefault(key, key);
                query.append(column).append(" = ?, ");
                parameters.add(value);

            }

            if (jsonObject.has("agency")) {
                JSONObject ag = jsonObject.getJSONObject("agency");
                if (ag.has("codicePartitaIVA") && ag.getString("codicePartitaIVA") != null) {
                    query.append("partitaiva = ?, ");
                    parameters.add(ag.getString("codicePartitaIVA"));
                }
            }

            query.setLength(query.length() - 2);

            query.append(" where "+idFiled+" = ?");

            PreparedStatement stmt = connection.getStatment(String.valueOf(query));
            try {
                int index = 1;
                for (Object param : parameters) {
                    stmt.setObject(index++, param); //parte da 1
                }

                stmt.setInt(index, changes.getIdUser());

                if (connection.makeQueryUpdate(stmt) == 0) {
                    throw new DietiEstateException("Utente con email " + changes.getEmail() + " non trovato");
                }

            }catch (SQLException e){
                logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
                throw new ErrorExecutingQuery();
            }
        }

    }

    public boolean isUserAbsent(Utente acquirente, String table) throws DietiEstateException {

        String query="SELECT * FROM "+ table +" where email = ?";

        //lancia eccezzione se non trova utente
        PreparedStatement stmt = prepareStatementGetUser(acquirente, query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) throw new UserAlreadyExists();
            return true;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    public boolean isUserPresent(Utente utente, String table) throws DietiEstateException {

        String query="SELECT * FROM "+table+" where email = ?";

        //lancia eccezzione se non trova utente
        PreparedStatement stmt = prepareStatementGetUser(utente, query);

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new UserNotExists();
            return true;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    public boolean isUserAbsentOverAll(Utente utente) throws DietiEstateException {


        String query="SELECT idacquirente as id_user,email, 'Acquirente' AS user_type FROM acquirente WHERE email like ? " +
                " UNION " +
                "SELECT idamministratore as id_user,email, 'Admin' AS user_type FROM amministratore WHERE  email like ? " +
                " UNION " +
                "SELECT idagente as id_user,email, 'Agent' AS user_type FROM agenteimmobiliare WHERE email like ? ";

        String emailUser = utente.getEmail();
        try {
            PreparedStatement stmt = connection.getStatment(query);
            stmt.setString(1,emailUser);
            stmt.setString(2,emailUser);
            stmt.setString(3,emailUser);
            connection.makeQuery(stmt);

            if(connection.hasNextRow()) throw new UserAlreadyExists();
            return true;
        } catch (SQLException | NullPointerException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }


    @Override
    public void close(){
        connection.close();
    }


}
