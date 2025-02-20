package DBLib.Postgres;

import DBLib.Interfacce.CommunicationWithDataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CommunicationWithPostgre implements CommunicationWithDataBase, AutoCloseable{

    private ResultSet result;
    private PreparedStatement stmt;
    private final ManagementConnectionPostgre managerConnection;

    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());


    public CommunicationWithPostgre() {
        this.managerConnection = new ManagementConnectionPostgre();
        this.managerConnection.createConnection();
    }

    @Override
    public void makeQuery(PreparedStatement stmt) throws SQLException {

        try {
            this.result = stmt.executeQuery();
            logger.info("Query executed successfully " + result.getStatement());
        } catch (SQLException e){
            logger.severe("Error executing query: " + e.getMessage());
            throw e;
        }

    }

    public void makeQueryUpdate(PreparedStatement stmt) throws SQLException {
        try {
            int rowsAffected = stmt.executeUpdate();
            logger.info("Query executed successfully, rows affected: " + rowsAffected);
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean nextRow() throws SQLException {
        if (this.result == null) {throw new SQLException();}
        try {
            if(!this.result.next())throw new SQLException();
            return true;
        } catch (SQLException e) {
            logger.severe("Error executing NextRow: " + e.getMessage());
            throw e;
        }

    }

    public PreparedStatement getStatment(String query){
        try {
            this.stmt = this.managerConnection.getConnection().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
        }
        return stmt;
    }




//    @Override
//    public boolean getNextRow() {
//        try {
//            if (this.result != null) {
//                logger.info("Next row fetched successfully.");
//                return true;
//            }
//            logger.warning("No more rows available.");
//        } catch (SQLException e) {
//            logger.severe("Error fetching next row: " + e.getMessage());
//        }
//        return false;
//    }

    @Override
    public int extractInt(String column) {
        try {
            if (this.result != null) {
                int value = this.result.getInt(column);
                logger.info("Extracted integer value: " + value);
                return value;
            }
        } catch (SQLException e) {
            logger.severe("Error extracting integer from column " + column + ": " + e.getMessage());
        }
        return 0;
    }

    @Override
    public String extractString(String column) {
        try {
            if (this.result != null) {
                String value = this.result.getString(column);
                logger.info("Extracted string value: " + value);
                return value;
            }
        } catch (SQLException e) {
            logger.severe("Error extracting string from column " + column + ": " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean extractBoolean(String column) {
        try {
            if (this.result != null) {
                boolean value = this.result.getBoolean(column);
                logger.info("Extracted boolean value: " + value);
                return value;
            }
        } catch (SQLException e) {
            logger.severe("Error extracting boolean from column " + column + ": " + e.getMessage());
        }
        return false;
    }

    @Override  // chiamato manualmnete o da autoclosable
    public void close() {
        try {
            if (this.result != null) {
                this.result.close();
                logger.info("ResultSet closed");
            }
            if (this.stmt != null) {
                this.stmt.close();
                logger.info("PreparedStatement closed");
            }
        } catch (SQLException e) {
            logger.severe("Error closing ResultSet or PreparedStatement: " + e.getMessage());
        } finally {
            // Ora possiamo chiudere la connessione al database
            logger.info("Closing database connection");
            this.managerConnection.closeConnection();
        }
    }

    public String getKeyCrypt() {
        return managerConnection.getKeycrypt();
    }

    public boolean hasNextRow() throws SQLException {
        boolean hasnext = !(result.next());
        result.previous();
        return hasnext;
    }
}