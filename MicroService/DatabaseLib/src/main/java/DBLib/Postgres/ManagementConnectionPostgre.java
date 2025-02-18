package DBLib.Postgres;

import java.nio.file.Path;
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
    private String database;

    private static final Logger logger = Logger.getLogger(ManagementConnectionPostgre.class.getName());


    public ManagementConnectionPostgre(String host, String user, String password, String port, String database) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
        this.database = database;
    }

    public ManagementConnectionPostgre() {
        readDataBaseAccessFile();
    }

    private void readDataBaseAccessFile() {

        try {
            String content = new String(Files.readAllBytes(Path.of("src/main/resources/credentialsPostgre.json")));
            JSONObject json = new JSONObject(content);

            this.host = json.getString("host");
            this.user = json.getString("user");
            this.password = json.getString("password");
            this.port = json.getString("port");
            this.database = json.getString("database");

        } catch (IOException e) {
            logger.severe("Error reading database access file: " + e.getMessage());
        }

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
        } catch (SQLException e) {
            logger.severe("Error closing connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}