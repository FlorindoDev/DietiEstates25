package DBLib.Postgres;

import java.util.logging.Logger;
//sql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ManagementConnectionPostgre {
    private Connection connection;
    private String host;
    private String password;
    private String user;
    private String port;
    private String database;
    private String keycrypt;

    private static final Logger logger = Logger.getLogger(ManagementConnectionPostgre.class.getName());


    public ManagementConnectionPostgre(String host, String user, String password, String port, String database, String keycrypt) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
        this.database = database;
        this.keycrypt = keycrypt;
    }

    public ManagementConnectionPostgre() {
        readEnvVariablesBaseAccessFile();
    }

    private void readEnvVariablesBaseAccessFile(){
        this.host = System.getenv("DB_HOST");
        this.user = System.getenv("DB_USER");
        this.password = System.getenv("DB_PASS");
        this.port = System.getenv("DB_PORT");
        this.database = System.getenv("DB_DATABASE_NAME");
        this.keycrypt = System.getenv("DB_CRYPTKEY");

    }

    public void createConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            this.connection = DriverManager.getConnection(url, user, password);

            logger.info("Connessione aperta con successo");

        } catch (ClassNotFoundException e) {
            logger.severe("DB driver not found: " + e.getMessage());
        } catch (SQLException e) {
            logger.severe("Connection failed: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            this.connection.close();
            this.connection = null;
        } catch (SQLException | NullPointerException e) {
            logger.severe("Error closing connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getKeycrypt() {
        return keycrypt;
    }
}