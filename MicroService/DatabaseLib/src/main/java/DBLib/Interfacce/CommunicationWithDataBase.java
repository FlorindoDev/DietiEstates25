package DBLib.Interfacce;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface CommunicationWithDataBase {
    void makeQuery(PreparedStatement stmt) throws SQLException;
    boolean hasNextRow() throws SQLException;
    // boolean getNextRow();
    int extractInt(String columnName);
    String extractString(String columnName);
    boolean extractBoolean(String columnName);
    void close();
}