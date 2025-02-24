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

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    protected PreparedStatement PrepareStatementGetUser(Utente utente, String Query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, utente.getEmail());
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;

    }

    private PreparedStatement PrepareStatementGetForLogin(Utente utente, String Query) throws DietiEstateException {

        PreparedStatement stmt = connection.getStatment(Query);

        try {

            int numero_di_parametri_query=6;
            String email = utente.getEmail();
            String password = utente.getPassword();

            for(int i=1;i<numero_di_parametri_query;i+=2){
                stmt.setString(i, email);
                stmt.setString(i+1, password);
            }

        } catch (Exception e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
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

            user = new Acquirente.Builder(connection.extractInt("id_user"), connection.extractString("email"))
                    .build();

        } else if (type_user.equals(Admin_type)) {

            user = new Admin.Builder(connection.extractInt("id_user"), connection.extractString("email"))
                    .build();

        } else if (type_user.equals(Agent_type)) {

            user = new Agent.Builder(connection.extractInt("id_user"), connection.extractString("email"))
                    .build();
        }
        return user;
    }

    @Override
    public Utente getUser(Utente utente) throws DietiEstateException {

        Utente user=null;
        String keycrypt = connection.getKeyCrypt();
        String Query="SELECT idacquirente as id_user,email, 'Acquirente' AS user_type FROM acquirente WHERE ? like email and password like crypt( ? , '"+ keycrypt +"')" +
                " UNION " +
                "SELECT idamministratore as id_user,email, 'Admin' AS user_type FROM amministratore WHERE crypt( ? , '$abcdefghijklmopqrstuv.') like email and password like crypt( ? , '"+ keycrypt +"')" +
                " UNION " +
                "SELECT idagente as id_user,email, 'Agent' AS user_type FROM agenteimmobiliare WHERE ? like email and password like crypt( ? , '"+ keycrypt +"')";


        PreparedStatement stmt = PrepareStatementGetForLogin(utente, Query);

        try {
            connection.makeQuery(stmt);
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        try {
            connection.nextRow();
            user = retrunEffectiveType(user);
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new UserNotFoundException();
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

    public boolean isUserAbsent(Utente acquirente, String table) throws DietiEstateException {

        String Query="SELECT * FROM "+ table +" where email = ?";

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

    protected boolean isUserPresent(Utente utente, String table) throws DietiEstateException {

        String Query="SELECT * FROM "+table+" where email = ?";

        //lancia eccezzione se non trova utente
        PreparedStatement stmt = PrepareStatementGetUser(utente, Query);

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
