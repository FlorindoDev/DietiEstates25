package Interfacce;

import java.sql.SQLException;

public interface CommunicationWithDataBase {
    boolean makeQuery(String query) throws SQLException;
    boolean hasNextRow();
    boolean getNextRow();
    int extractInt(String columnName);
    String extractString(String columnName);
    boolean extractBoolean(String columnName);
}

