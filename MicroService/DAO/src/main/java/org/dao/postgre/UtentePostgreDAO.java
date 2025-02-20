package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.UtenteDAO;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.UserNotFoundException;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UtentePostgreDAO implements UtenteDAO {

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    protected void PrepareStatmentGetUserAndContactDB(Utente utente, String Query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, utente.getEmail());
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }


        try {
            connection.makeQuery(stmt);
        } catch (SQLException e) {
            throw new ErrorExecutingQuery();
        }


        try {
            connection.hasNextRow();
        } catch (SQLException e) {
            throw new UserNotFoundException();
        }
    }

    private PreparedStatement PrepareStatmentGetForLogin(Utente utente,String Query) throws DietiEstateException {

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
            logger.severe("Error executing query: " + e.getMessage());
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


        PreparedStatement stmt = PrepareStatmentGetForLogin(utente, Query);

        try {
            connection.makeQuery(stmt);
        } catch (SQLException e) {
            throw new ErrorExecutingQuery();
        }

        try {
            connection.hasNextRow();
            user = retrunEffectiveType(user);
        } catch (SQLException e) {
            throw new UserNotFoundException();
        }
        return user;
    }




}
