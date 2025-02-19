package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.UtenteDAO;
import org.exc.DietiEstateException;
import org.exc.ErrorExecutingQuery;
import org.exc.UserNotFoundException;
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

    protected void PrepareStatmentAndContactDB(Utente utente, String Query) throws ErrorExecutingQuery {
        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, utente.getEmail());
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        connection.makeQuery(stmt);

        //TODO mettere controllo per quando è vuoto
        connection.hasNextRow();
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

        String Query="SELECT idacquirente as id_user,email, 'Acquirente' AS user_type FROM acquirente WHERE ? like email and password like cryp( ? , '$abcdefghijklmopqrstuv.')" +
                " UNION " +
                "SELECT idamministratore as id_user,email, 'Admin' AS user_type FROM amministratore WHERE crypt( ? , '$abcdefghijklmopqrstuv.') like email and password like crypt( ? , '$abcdefghijklmopqrstuv.')" +
                " UNION " +
                "SELECT idagente as id_user,email, 'Agent' AS user_type FROM agenteimmobiliare WHERE ? like email and password like crypt( ? , '$abcdefghijklmopqrstuv.')";


        PreparedStatement stmt = connection.getStatment(Query);

        try {

            int numero_di_parametri_query=6;
            String email = utente.getEmail();
            String password = utente.getPassword();

            for(int i=1;i<numero_di_parametri_query;i+=2){
                stmt.setString(i, email);
                stmt.setString(i+1, password);
            }

        } catch (SQLException e) {
            //TODO mettere nostra eccezzione
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();

        }

        connection.makeQuery(stmt);
        Utente user=null;

        if(connection.hasNextRow()){

            user = retrunEffectiveType(user);

        }else{

            throw new UserNotFoundException();
        }
        return user;
    }


}
