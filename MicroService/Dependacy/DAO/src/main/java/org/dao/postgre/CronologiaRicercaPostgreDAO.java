package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AgentDAO;
import org.dao.Interfacce.CronologiaRicercaDAO;
import org.dao.Interfacce.EstateDAO;
import org.exc.DataBaseException.AddressAlreadyExists;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.EstateNotExists;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.ClasseEnergetica.ConverterEnergeticClass;
import org.md.Estate.ClasseEnergetica.EnergeticClass;
import org.md.Estate.Estate;
import org.md.Estate.EstateFilter;
import org.md.Estate.Mode.ConverterMode;
import org.md.Estate.Mode.Mode;
import org.md.Estate.Status.ConverterStatus;
import org.md.Estate.Status.Status;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Utente.Agent;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CronologiaRicercaPostgreDAO implements CronologiaRicercaDAO {

    private static final String TABLE = "immobile";
    protected static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private int numberParamsOfFilter = 1;
    private CommunicationWithPostgre connection;

    private static final Logger logger = Logger.getLogger(CronologiaRicercaPostgreDAO.class.getName());



    public CronologiaRicercaPostgreDAO() {
        this.connection = new CommunicationWithPostgre();

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