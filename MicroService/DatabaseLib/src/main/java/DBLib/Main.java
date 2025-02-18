package DBLib;

import DBLib.Postgres.CommunicationWithPostgre;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        CommunicationWithPostgre comm = new CommunicationWithPostgre();
        comm.makeQuery("SELECT * FROM Acquirente;");

    }
}