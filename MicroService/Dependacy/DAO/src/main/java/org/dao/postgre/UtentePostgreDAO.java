package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.UtenteDAO;
import org.exc.DataBaseException.*;
import org.exc.DietiEstateException;
import org.json.JSONObject;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UtentePostgreDAO implements UtenteDAO {

    public static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    public static final String ID_USER_COLUMN = "id_user";
    public static final String EMAIL_COLUMN = "email";
    private final CommunicationWithPostgre connection;
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

    public Utente getUserFromEmail(Utente utente) throws DietiEstateException {

        Utente user=null;

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

            if(!connection.hasNextRow()) throw new UserNotExists();

            connection.nextRow();
            user = retrunEffectiveType(user);

        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        return user;
    }


    public void updateUser(Utente changes, String tabella) throws DietiEstateException {

        JSONObject jsonObject = new JSONObject(changes.TranslateToJson());
        if (!jsonObject.isEmpty()) { // per evitare di fare update a vuoto

            StringBuilder query = new StringBuilder("UPDATE "+tabella+" SET ");
            List<Object> parameters = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);

                if (!ID_USER_COLUMN.equals(key) && !EMAIL_COLUMN.equals(key) && !"null".equals(value.toString())) {
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

                connection.makeQueryUpdate(stmt);
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
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }


}
