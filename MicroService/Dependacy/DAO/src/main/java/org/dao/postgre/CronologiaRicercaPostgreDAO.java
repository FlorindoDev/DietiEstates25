package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;

import org.dao.Interfacce.CronologiaRicercaDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import java.sql.*;
import java.util.logging.Logger;

public class CronologiaRicercaPostgreDAO implements CronologiaRicercaDAO {

    protected static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private final CommunicationWithPostgre connection;

    private static final Logger logger = Logger.getLogger(CronologiaRicercaPostgreDAO.class.getName());


    public CronologiaRicercaPostgreDAO() {
        this.connection = new CommunicationWithPostgre();

    }

    @Override
    public void close(){
        if(connection != null)connection.close();
    }


    @Override
    public void add(String queryParams, String email) throws DietiEstateException{
        String query = """
          INSERT INTO ricerca (comando, idacquirente)
          VALUES (?, (SELECT idacquirente FROM acquirente WHERE email = ?))
        """;

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, queryParams);
            stmt.setString(2, email);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try{
            connection.makeQueryUpdate(stmt);
        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }
}