package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.IndirizzoDAO;
import org.exc.DataBaseException.*;
import org.exc.DietiEstateException;
import org.md.Geolocalizzazione.Indirizzo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.dao.postgre.EstatePostgreDAO.ERROR_EXECUTING_QUERY;

public class IndirizzoPostgreDAO implements IndirizzoDAO {

    private static final String TABLE = "indirizzo";
    private final CommunicationWithPostgre connection;
    private static final Logger logger = Logger.getLogger(IndirizzoPostgreDAO.class.getName());

    public IndirizzoPostgreDAO(CommunicationWithPostgre connection){
        this.connection = connection;
    }

    public IndirizzoPostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public Indirizzo getIndirizzoFromId(int idindirizzo) throws DietiEstateException {

        String query = "SELECT * FROM"+TABLE+"WHERE idindirizzo = ?";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setInt(1,idindirizzo);
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new AddressNotExists();
            connection.nextRow();

            return new Indirizzo.Builder<>(connection.extractInt("idindirizzo"))
                    .setCap(connection.extractInt("cap"))
                    .setCitta(connection.extractString("citta"))
                    .setVia(connection.extractString("via"))
                    .setNumeroCivico(connection.extractString("numerocivico"))
                    .setStato(connection.extractString("stato"))
                    .build();

        }catch(SQLException e){
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public boolean isAddressExistsByID(int idindirizzo) throws DietiEstateException {

        String query = "SELECT * FROM "+TABLE+" WHERE idindirizzo = ?";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setInt(1, idindirizzo);
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new AddressNotExists();
            return true;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }finally {
            connection.close();
        }
    }

    @Override
    public boolean isAddressExistsByALL(Indirizzo addr) throws DietiEstateException {

        String query;
        if (addr.getIdIndirizzo() == 0) query="SELECT * FROM "+TABLE+" WHERE stato = ? AND citta = ? AND via = ? AND numeroCivico = ? AND cap = ? AND quartiere = ?";
        else query = "SELECT * FROM "+TABLE+" WHERE idindirizzo = ? AND stato = ? AND citta = ? AND via = ? AND numeroCivico = ? AND cap = ? AND quartiere = ?";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            int idx = 0;
            if (addr.getIdIndirizzo() == 0) { stmt.setInt(++idx, addr.getIdIndirizzo()); }
            stmt.setString(++idx, addr.getStato());
            stmt.setString(++idx, addr.getCitta());
            stmt.setString(++idx, addr.getVia());
            stmt.setString(++idx, addr.getNumeroCivico());
            stmt.setInt(++idx, addr.getCap());
            if (addr.getQuartiere().isEmpty()) {
                stmt.setString(++idx, addr.getQuartiere());
            }else{
                stmt.setString(++idx, null);
            }

            connection.makeQuery(stmt);

            if(!connection.hasNextRow()) throw new AddressNotExists();
            return true;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    @Override
    public boolean isAddressNotExistsByALL(Indirizzo addr) throws DietiEstateException {

        String query;
        if (addr.getIdIndirizzo() == 0) query = "SELECT * FROM "+TABLE+" WHERE stato = ? AND citta = ? AND via = ? AND numeroCivico = ? AND cap = ? AND quartiere = ?";
        else query = "SELECT * FROM "+TABLE+" WHERE idindirizzo = ? AND stato = ? AND citta = ? AND via = ? AND numeroCivico = ? AND cap = ?  AND quartiere = ?";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            int idx = 0;
            if (addr.getIdIndirizzo() != 0) { stmt.setInt(++idx, addr.getIdIndirizzo()); }
            stmt.setString(++idx, addr.getStato());
            stmt.setString(++idx, addr.getCitta());
            stmt.setString(++idx, addr.getVia());
            stmt.setString(++idx, addr.getNumeroCivico());
            stmt.setInt(++idx, addr.getCap());
            if (addr.getQuartiere().isEmpty()) {
                stmt.setString(++idx, addr.getQuartiere());
            }else{
                stmt.setString(++idx, null);
            }

            connection.makeQuery(stmt);

            if(connection.hasNextRow()) throw new AddressAlreadyExists();
            return true;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }


    @Override
    public boolean createAddress(Indirizzo indirizzo) throws DietiEstateException {

        String query = "INSERT INTO "+TABLE+" (stato, citta, via, numerocivico, cap, quartiere) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = connection.getStatment(query);

        int idx = 0;
        try {
            stmt.setString(++idx, indirizzo.getStato());
            stmt.setString(++idx, indirizzo.getCitta());
            stmt.setString(++idx, indirizzo.getVia());
            stmt.setString(++idx, indirizzo.getNumeroCivico());
            stmt.setInt(++idx, indirizzo.getCap());
            stmt.setString(++idx, indirizzo.getQuartiere());

            connection.makeQueryUpdate(stmt);
            return true;
        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public int getLastAddressId() throws DietiEstateException {

        String query = "SELECT MAX(idindirizzo) FROM "+TABLE;

        PreparedStatement stmt = connection.getStatment(query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()){
                connection.nextRow();

                int idAddr  = connection.extractInt("idindirizzo");

                if (idAddr == 0) throw new AddressNotExists();

                return idAddr;

            }else {
                throw new AddressNotExists();
            }
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }
    @Override
    public void close(){
        connection.close();
    }

}
