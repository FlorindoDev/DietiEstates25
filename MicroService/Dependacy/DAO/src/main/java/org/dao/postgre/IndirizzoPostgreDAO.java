package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.IndirizzoDAO;
import org.exc.DataBaseException.*;
import org.exc.DietiEstateException;
import org.md.Estate.Indirizzo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class IndirizzoPostgreDAO implements IndirizzoDAO {

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(IndirizzoPostgreDAO.class.getName());


    @Override
    public Indirizzo getIndirizzoFromId(int idindirizzo) throws DietiEstateException {

        String query = "SELECT * FROM indirizzo WHERE idindirizzo = ?";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setInt(1,idindirizzo);
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new AddressNotExists();
            connection.nextRow();

            return new Indirizzo.Builder<>(connection.extractInt("idindirizzo"))
                    .setCap(connection.extractInt("cap"))
                    .setCitta(connection.extractString("citta"))
                    .setVia(connection.extractString("via"))
                    .setNumeroCivico(connection.extractString("numerocivico"))
                    .setStato(connection.extractString("stato"))
                    .build();

        }catch(SQLException e){
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }
}
