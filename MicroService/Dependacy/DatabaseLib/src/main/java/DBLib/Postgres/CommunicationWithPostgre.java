package DBLib.Postgres;

import DBLib.Interfacce.CommunicationWithDataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationWithPostgre implements CommunicationWithDataBase, AutoCloseable{

    public static final String ERROR_EXECUTING_QUERY = "Error executing query: ";
    private ResultSet result;
    private PreparedStatement stmt;
    private final ManagementConnectionPostgre managerConnection;

    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public CommunicationWithPostgre() {
        this.managerConnection = new ManagementConnectionPostgre();
        this.managerConnection.createConnection();
        if (logger.isLoggable(Level.INFO)) {
            // Qui è lecito chiamare il getStackTrace() perché sappiamo che almeno INFO è abilitato
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            if (stack.length > 2) {
                logger.log(
                        Level.INFO,
                        "Chiamato da: {0}#{1}",
                        new Object[]{stack[2].getClassName(), stack[2].getMethodName()}
                );
            }
        }
    }

    public int countPlaceholders(String query) {
        return query.length() - query.replace("?", "").length();
    }
    @Override
    public void makeQuery(PreparedStatement stmt) throws SQLException {

        try {
            this.result = stmt.executeQuery();
            logger.info("Query executed successfully " + result.getStatement());
        } catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw e;
        }

    }

    public int makeQueryUpdate(PreparedStatement stmt) throws SQLException {
        int rowsAffected;
        try {
            logger.info("I going to executed: " + stmt.toString());
            rowsAffected = stmt.executeUpdate();
            logger.info("Query executed successfully, rows affected: " + rowsAffected);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw e;
        }
        return rowsAffected;
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
            if(stmt != null) stmt.close();
            this.stmt = this.managerConnection.getConnection().prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
        }
        return stmt;
    }


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
    public double extractDouble(String column) {
        try {
            if (this.result != null) {
                double value = this.result.getDouble(column);
                logger.info("Extracted double value: " + value);
                return value;
            }
        } catch (SQLException e) {
            logger.severe("Error extracting double from column " + column + ": " + e.getMessage());
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

    public Date extractDate(String column) {
        try {
            if (this.result != null) {
                Date value = this.result.getDate(column);
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
            if (logger.isLoggable(Level.INFO)) {
                // Qui è lecito chiamare il getStackTrace() perché sappiamo che almeno INFO è abilitato
                StackTraceElement[] stack = Thread.currentThread().getStackTrace();
                if (stack.length > 2) {
                    logger.log(
                            Level.INFO,
                            "Chiamato da: {0}#{1}",
                            new Object[]{stack[2].getClassName(), stack[2].getMethodName()}
                    );
                }
            }
            this.managerConnection.closeConnection();
        }
    }

    public String getKeyCrypt() {
        return managerConnection.getKeycrypt();
    }

    public boolean hasNextRow() throws SQLException {
        boolean hasnext = result.next();
        result.previous();
        return hasnext;
    }

    public void setAutoCommit(boolean bool){
        try {
            managerConnection.getConnection().setAutoCommit(bool);
        } catch (SQLException e) {
            logger.severe("Error Imposting auto commit to " + bool + " : " + e.getMessage());
        }

    }

    public void commitActions() throws SQLException {
        managerConnection.getConnection().commit();
    }

    public void rollBackAction(){
        try {
            managerConnection.getConnection().rollback();
        } catch (SQLException e) {
            logger.severe("Error to rollback:" + e.getMessage());
        }
    }

    public ResultSet getResult(){
        return this.result;
    }


}