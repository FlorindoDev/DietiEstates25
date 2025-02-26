package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.EstateDAO;

import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.EstateNotExists;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;

public class EstatePostgreDAO implements EstateDAO {

    private static final String TABLE = "immobile";
    public static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";

    private CommunicationWithPostgre connection;

    private static final Logger logger = Logger.getLogger(EstatePostgreDAO.class.getName());

    public EstatePostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public void createEstate(Estate newEstate) throws DietiEstateException {

        String query = "INSERT INTO " + TABLE + " (" +
                "idagente, idindirizzo, partitaIVA, foto, descrizione, prezzo, dimensioni, stanze, piano, bagni, garage, ascensore, classeenergetica, modalita, stato) VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.getStatment(query)) {
            int index = 1;

            stmt.setInt(index++, newEstate.getAgente().getIdUser());
            stmt.setInt(index++, newEstate.getIndirizzo().getIdIndirizzo());
            stmt.setString(index++, newEstate.getAgenzia().getCodicePartitaIVA());
            stmt.setString(index++, newEstate.getFoto());
            stmt.setString(index++, newEstate.getDescrizione());
            stmt.setDouble(index++, newEstate.getPrice());
            stmt.setDouble(index++, newEstate.getSpace());
            stmt.setInt(index++, newEstate.getRooms());
            stmt.setInt(index++, newEstate.getFloor());
            stmt.setInt(index++, newEstate.getWc());
            stmt.setInt(index++, newEstate.getGarage());
            stmt.setBoolean(index++, newEstate.getElevator());
            stmt.setString(index++, newEstate.getClasseEnergetica().getEnergeticClass());
            stmt.setObject(index++, newEstate.getMode().getName(), Types.OTHER);
            stmt.setObject(index++, newEstate.getStato().getName(), Types.OTHER);

            connection.makeQueryUpdate(stmt);

        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public String changeEstate(Estate changEstate) {
        return "";
    }

    @Override
    public Agent getAgent(Estate agency) {
        return null;
    }

    @Override
    public void addEstateAgent(Estate estate, Agent agent) throws DietiEstateException {
        String query= "UPDATE immobile SET idagent = ? WHERE idimmobile = ? ";

        PreparedStatement stmt;
        try {
            stmt = connection.getStatment(query);
            stmt.setInt(1, agent.getIdUser());
            stmt.setInt(2, estate.getIdEstate());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        makeEstateUpdate(stmt);

    }


    private int makeEstateUpdate(PreparedStatement stmt) throws ErrorExecutingQuery {
        int res = 0;
        try {
            res = connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
        return res;
    }

    @Override
    public boolean isEstatePresent(Estate estate) throws DietiEstateException {
        String query="SELECT * FROM immobile where idimmobile = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.getStatment(query);

            stmt.setInt(1, estate.getIdEstate());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new EstateNotExists();
            return true;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

}