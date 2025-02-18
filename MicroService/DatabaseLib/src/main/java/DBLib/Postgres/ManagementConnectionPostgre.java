package DBLib.Postgres;

import java.util.logging.Logger;
import java.io.IOException;
//file
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private static final Logger logger = Logger.getLogger(ManagementConnectionPostgre.class.getName());


    public ManagementConnectionPostgre(String host, String user, String password, String port) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public ManagementConnectionPostgre() {
        readDataBaseAccessFile();
    }

    private void readDataBaseAccessFile() {

        try {
            String content = new String(Files.readAllBytes(Paths.get("credentialsPostgre.json")));
            JSONObject json = new JSONObject(content);

            this.host = json.getString("host");
            this.user = json.getString("user");
            this.password = json.getString("password");
            this.port = json.getString("port");

        } catch (IOException e) {
            logger.severe("Error reading database access file: " + e.getMessage());
        }

    }

    public void createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.user, this.password, this.port);
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
        } catch (SQLException e) {
            logger.severe("Error closing connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}