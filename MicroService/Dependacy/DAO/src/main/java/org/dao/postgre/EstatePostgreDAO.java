package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.EstateDAO;

import org.exc.DataBaseException.*;
import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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

        IndirizzoPostgreDAO addrsDao = new IndirizzoPostgreDAO(connection);

        int idAddress = 0;

        try {
            addrsDao.isAddressNotExistsByALL(newEstate.getIndirizzo()); // vuole crearlo, verifico se gai esiste
            // se non solleva eccezioni significa che non esiste, procedo a crarlo
            connection.setAutoCommit(false);
            addrsDao.createAddress(newEstate.getIndirizzo());
            idAddress = addrsDao.getLastAddressId();

        } catch (AddressAlreadyExists e) {
            logger.info("sono nel catch");
            try {
                connection.nextRow();
            } catch (SQLException e1) {
                logger.severe(ERROR_EXECUTING_QUERY + e1.getMessage());
                throw new ErrorExecutingQuery();
            }
            idAddress = connection.extractInt("idindirizzo");
        }

        logger.log(Level.INFO, "ID Address: {0}", idAddress);



        try (PreparedStatement stmt = connection.getStatment(query)) {

            int index = 0;

            stmt.setInt(++index, newEstate.getAgente().getIdUser());
            stmt.setInt(++index, idAddress);
            stmt.setString(++index, newEstate.getAgenzia().getCodicePartitaIVA());
            stmt.setString(++index, newEstate.getFoto());
            stmt.setString(++index, newEstate.getDescrizione());
            stmt.setDouble(++index, newEstate.getPrice());
            stmt.setDouble(++index, newEstate.getSpace());
            stmt.setInt(++index, newEstate.getRooms());
            stmt.setInt(++index, newEstate.getFloor());
            stmt.setInt(++index, newEstate.getWc());
            stmt.setInt(++index, newEstate.getGarage());
            stmt.setBoolean(++index, newEstate.getElevator());
            stmt.setString(++index, newEstate.getClasseEnergetica().getNome());
            stmt.setObject(++index, newEstate.getMode().getName(), Types.OTHER);
            stmt.setObject(++index, newEstate.getStato().getName(), Types.OTHER);


            connection.makeQueryUpdate(stmt);
            connection.commitActions();

        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
             connection.rollBackAction();
            throw new ErrorExecutingQuery();
        }finally {
            connection.setAutoCommit(true);
        }


    }

    @Override
    public void updateEstate(Estate estate) throws DietiEstateException{

        StringBuilder query = new StringBuilder(String.format("UPDATE %s SET ", TABLE));
        List<Object> params = new ArrayList<>();

        if (estate.getAgente() != null) {
            query.append("idagente = ?, ");
            params.add(estate.getAgente().getIdUser());
        }
        if (estate.getIndirizzo() != null) {
            query.append("idindirizzo = ?, ");
            params.add(estate.getIndirizzo().getIdIndirizzo());
        }
        if (estate.getAgenzia() != null) {
            query.append("partitaiva = ?, ");
            params.add(estate.getAgenzia().getCodicePartitaIVA());
        }
        if (!estate.getFoto().isEmpty()) {
            query.append("foto = ?, ");
            params.add(estate.getFoto());
        }
        if (!estate.getDescrizione().isEmpty()) {
            query.append("descrizione = ?, ");
            params.add(estate.getDescrizione());
        }
        if (estate.getPrice() != 0) {
            query.append("prezzo = ?, ");
            params.add(estate.getPrice());
        }
        if (estate.getSpace() != 0) {
            query.append("dimensioni = ?, ");
            params.add(estate.getSpace());
        }
        if (estate.getRooms() != 0) {
            query.append("stanze = ?, ");
            params.add(estate.getRooms());
        }

        if (params.isEmpty()) {
            return; // Nessun aggiornamento necessario
        }

        query.setLength(query.length() - 2); // Rimuove l'ultima virgola
        query.append(" WHERE idimmobile = ?");
        params.add(estate.getIdEstate());


        try {

            PreparedStatement stmt = connection.getStatment(String.valueOf(query));

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            connection.makeQueryUpdate(stmt);

        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public Agent getAgent(Estate agency) {
        return null;
    }

    @Override
    public void updateEstateAgent(Estate estate, Agent agent) throws DietiEstateException {

        PreparedStatement stmt;
        String query;

        query= "UPDATE immobile SET idagente = ? WHERE idimmobile = ? ";
        
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