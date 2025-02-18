package DBLib.Postgres;

import Interfacce.CommunicationWithDataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunicationWithPostgre implements CommunicationWithDataBase{

    private ResultSet result;
    private final ManagementConnectionPostgre managerConnection;

    public CommunicationWithPostgre() {
        this.managerConnection = new ManagementConnectionPostgre();
        this.managerConnection.createConnection();
    }

    @Override
    public boolean makeQuery(String query) throws SQLException {

        PreparedStatement stmt = this.managerConnection.getConnection().prepareStatement(query);
        this.result = stmt.executeQuery();

    }

    @Override
    public boolean hasNextRow() {
        return false;
    }

    @Override
    public boolean getNextRow() {
        return false;
    }

    @Override
    public int extractInt(String column) {
        return 0;
    }

    @Override
    public String extractString(String column) {
        return null;
    }

    @Override
    public boolean extractBoolean(String column) {
        // Implementazione per estrarre un valore booleano
        return false;
    }

}