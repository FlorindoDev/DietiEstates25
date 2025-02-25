package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AdminDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DietiEstateException;
import org.md.Utente.Admin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AdminPostgreDAO extends UtentePostgreDAO implements AdminDAO {

    private final String TABLE = "amministratore";

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public AdminPostgreDAO() {}

    @Override
    public Admin getUser(Admin admin) throws DietiEstateException {

        String Query="SELECT * FROM amministratore where ? = email";

        PreparedStatement stmt = PrepareStatementGetUser(admin, Query);

        try {
            connection.makeQuery(stmt);
            connection.nextRow();
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        admin = new Admin.Builder(connection.extractInt("idamministratore"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .setIsSupport(connection.extractBoolean("IsSupportoAmministratore"))
                .build();

        return admin;
    }



    @Override
    public void createUser(Admin admin) throws DietiEstateException{

        String Query=
                "INSERT INTO amministratore(email, issupportoamministratore, nome, cognome, password, partitaiva) VALUES" +
                        "(?,?,?,?,crypt(?,'" +
                        connection.getKeyCrypt() +
                        "'),?)";

        System.out.println(Query);
        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, admin.getEmail());
            stmt.setBoolean(2, admin.getSupport());
            stmt.setString(3, admin.getNome());
            stmt.setString(4, admin.getCognome());
            stmt.setString(5, admin.getPassword());
            stmt.setString(6, admin.getAgency().getCodice_partitaIVA());
            System.out.println(stmt.toString());
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
    public void updateUser(Admin utente) throws DietiEstateException {
        super.updateUser(utente, TABLE);
    }

    @Override
    public void removeAdmin(Admin admin) throws DietiEstateException {

        String Query= "DELETE FROM amministratore WHERE email = ?";
        System.out.println(Query);
        PreparedStatement stmt = connection.getStatment(Query);
        System.out.println(Query);
        try {
            stmt.setString(1, admin.getEmail());
            System.out.println(stmt.toString());
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        int res = 0;
        try {
            res = connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

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

        String Query= "UPDATE amministratore SET issupportoamministratore = 'TRUE' WHERE email = ? ";

        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, admin.getEmail());
            System.out.println(stmt.toString());
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
    public boolean isUserPresent(Admin user) throws DietiEstateException {

        return super.isUserPresent(user, TABLE);

    }

    public boolean isUserAbsentOverAll(Admin user) throws DietiEstateException {

        return super.isUserAbsentOverAll(user);

    }

    @Override
    public void downgradeSupport(Admin admin) throws  DietiEstateException{

        String Query= "UPDATE amministratore SET issupportoamministratore = 'FALSE' WHERE email = ? ";

        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, admin.getEmail());
            System.out.println(stmt.toString());
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


}
