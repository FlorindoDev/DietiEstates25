package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.UtenteDAO;
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

    private void PrepareStatmentAndContactDB(Utente utente, String Query) {
        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, utente.getEmail());
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new RuntimeException(e);
        }

        connection.makeQuery(stmt);

        //mettere controllo per quando è vuoto TODO
        connection.hasNextRow();
    }

    @Override
    public Utente getUser(Utente utente) {

        String Query="SELECT idacquirente as id_user, email,password, 'Acquirente' AS user_type FROM acquirente WHERE ? like email and password like ?" +
                " UNION " +
                "SELECT idamministratore as id_user, email,password, 'Admin' AS user_type FROM amministratore WHERE ? like email and password like ?" +
                " UNION " +
                "SELECT idagente as id_user, email,password, 'Agent' AS user_type FROM agenteimmobiliare WHERE ? like email and password like ?";


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
            logger.severe("Error executing query: " + e.getMessage());
            throw new RuntimeException(e);
        }

        connection.makeQuery(stmt);
        Utente user=null;
        //mettere controllo per quando è vuoto TODO
        if(connection.hasNextRow()){

            String type_user = connection.extractString("user_type");
            String Acquirente_type = Acquirente.class.getSimpleName();
            String Admin_type = Admin.class.getSimpleName();
            String Agent_type = Agent.class.getSimpleName();

            if(type_user.equals(Acquirente_type)){

                user = new Acquirente.Builder(connection.extractInt("id_user"), connection.extractString("email"))
                        .setPassword(connection.extractString("password"))
                        .build();

            } else if (type_user.equals(Admin_type)) {

                user = new Admin.Builder(connection.extractInt("id_user"), connection.extractString("email"))
                        .setPassword(connection.extractString("password"))
                        .build();

            } else if (type_user.equals(Agent_type)) {

                user = new Agent.Builder(connection.extractInt("id_user"), connection.extractString("email"))
                        .setPassword(connection.extractString("password"))
                        .build();
            }

        }else{
            //Lanciare Exception TODO
        }
        return user;
    }

    @Override
    public Admin getUser(Admin admin) {

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
    public Agent getUser(Agent agent) {
        String Query="SELECT * FROM agenteimmobiliare where ? = email";

        PrepareStatmentAndContactDB(agent, Query);

        agent = new Agent.Builder(connection.extractInt("idagente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setBiografia(connection.extractString("biografia"))
                .setBiografia(connection.extractString("ImmagineProfilo"))
                .build();

        return agent;
    }

    @Override
    public Acquirente getUser(Acquirente acquirente) {

        String Query="SELECT * FROM acquirente where ? = email";

        PrepareStatmentAndContactDB(acquirente, Query);

        acquirente = new Acquirente.Builder(connection.extractInt("idagente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .build();

        return acquirente;
    }

    @Override
    public void createUser(Acquirente acquirente) {

    }

    @Override
    public void createUser(Admin admin) {

    }

    @Override
    public void createUser(Agent agent) {

    }

    @Override
    public void updateUser(Acquirente changes) {

    }

    @Override
    public void updateUser(Admin changes) {

    }

    @Override
    public void updateUser(Agent changes) {

    }
}
