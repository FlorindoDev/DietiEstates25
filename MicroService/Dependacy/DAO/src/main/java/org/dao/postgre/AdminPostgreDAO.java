package org.dao.postgre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.dao.Interfacce.AdminDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DietiEstateException;
import org.md.Utente.Admin;

import DBLib.Postgres.CommunicationWithPostgre;

public class AdminPostgreDAO extends UtentePostgreDAO implements AdminDAO {

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private static final String TABLE = "amministratore";
    private static final String ID_DB_FIELD = "idamministratore";


    private static final Logger logger = Logger.getLogger(AdminPostgreDAO.class.getName());

    public AdminPostgreDAO() {
        super();
    }

    public AdminPostgreDAO(CommunicationWithPostgre connection) {
        super(connection);
    }

    @Override
    public Admin getUser(Admin admin) throws DietiEstateException {

        String query="SELECT * FROM amministratore where ? = email";

        PreparedStatement stmt = prepareStatementGetUser(admin, query);

        try {
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        admin = new Admin.Builder(connection.extractInt(ID_DB_FIELD), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setIsSupport(connection.extractBoolean("IsSupportoAmministratore"))
                .build();

        return admin;
    }



    @Override
    public void createUser(Admin admin) throws DietiEstateException{

        String query=
                "INSERT INTO amministratore(email, issupportoamministratore, nome, cognome, password, partitaiva) VALUES" +
                        "(?,?,?,?,crypt(?,'" +
                        connection.getKeyCrypt() +
                        "'),?)";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, admin.getEmail());
            stmt.setBoolean(2, admin.getSupport());
            stmt.setString(3, admin.getNome());
            stmt.setString(4, admin.getCognome());
            stmt.setString(5, admin.getPassword());
            stmt.setString(6, admin.getAgency().getCodicePartitaIVA());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        makeAdminUpdate(stmt);
    }

    @Override
    public void updateUser(Admin utente) throws DietiEstateException {
        super.updateUser(utente, TABLE, ID_DB_FIELD);
    }

    @Override
    public void removeAdmin(Admin admin) throws DietiEstateException {

        String query= "DELETE FROM amministratore WHERE email = ?";
        PreparedStatement stmt = makeAdminQuery(admin, query);

        int res = makeAdminUpdate(stmt);

        if(res == 0){
            throw new UserNotExists();
        }

    }


    @Override
    public boolean isUserAbsent(Admin user) throws DietiEstateException {

        return super.isUserAbsent(user, TABLE);

    }

    @Override
    public void upgradeSupportAdmin(Admin admin) throws DietiEstateException {

        String query= "UPDATE amministratore SET issupportoamministratore = 'TRUE' WHERE email = ? ";

        PreparedStatement stmt = makeAdminQuery(admin, query);

        makeAdminUpdate(stmt);


    }
    public boolean isUserPresent(Admin user) throws DietiEstateException {

        return super.isUserPresent(user, TABLE);

    }

    public boolean isUserAbsentOverAll(Admin user) throws DietiEstateException {

        return super.isUserAbsentOverAll(user);

    }

    @Override
    public void downgradeSupport(Admin admin) throws  DietiEstateException{

        String query= "UPDATE amministratore SET issupportoamministratore = 'FALSE' WHERE email = ? ";

        PreparedStatement stmt = makeAdminQuery(admin, query);

        makeAdminUpdate(stmt);

    }

    private PreparedStatement makeAdminQuery(Admin admin, String query) throws ErrorCreateStatment {
        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, admin.getEmail());

        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }
        return stmt;
    }

    private int makeAdminUpdate(PreparedStatement stmt) throws ErrorExecutingQuery {
        int res = 0;
        try {
            res = connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
        return res;
    }


}
