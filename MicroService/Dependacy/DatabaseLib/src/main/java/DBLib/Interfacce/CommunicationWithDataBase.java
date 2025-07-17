package DBLib.Interfacce;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface CommunicationWithDataBase {
    void makeQuery(PreparedStatement stmt) throws SQLException;
    int makeQueryUpdate(PreparedStatement stmt) throws SQLException;
    boolean nextRow() throws SQLException;
 
    int extractInt(String columnName);
    public double extractDouble(String column);
    String extractString(String columnName);
    boolean extractBoolean(String columnName);
    void setAutoCommit(boolean bool);
    void commitActions() throws SQLException;
    public void rollBackAction();
    void close();
}