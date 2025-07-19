package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;

import org.dao.Interfacce.CronologiaRicercaDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.md.Estate.Ricerca;
import org.md.Utente.Utente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

        if(checkExists(queryParams,email)) return;

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

    private boolean checkExists(String queryParams, String email) throws DietiEstateException {
        String query= "SELECT * " +
                "FROM ricerca INNER JOIN acquirente ON ricerca.idacquirente = acquirente.idacquirente  " +
                "where ? = acquirente.email AND ricerca.comando = ?";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, email);
            stmt.setString(2, queryParams);
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }


        try{
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) return true;

        }catch(SQLException e){
            return false;
        }

        return false;

    }

    @Override
    public List<Ricerca> get(Utente utente) throws DietiEstateException {

        List<Ricerca> ricerche = new ArrayList<>();
        String query="SELECT * FROM ricerca INNER JOIN acquirente ON ricerca.idacquirente = acquirente.idacquirente  where ? = acquirente.email";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, utente.getEmail());
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try{
            connection.makeQuery(stmt);
            while(connection.hasNextRow()){
                connection.nextRow();
                ricerche.add(new Ricerca(connection.extractInt("idricerca"),connection.extractString("comando"),connection.extractInt("idacquirente")));

            }
        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }



        return ricerche;
    }
}