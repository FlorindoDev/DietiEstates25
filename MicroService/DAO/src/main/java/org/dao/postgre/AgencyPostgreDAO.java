package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AgencyDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AgencyPostgreDAO implements AgencyDAO {

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    @Override
    public void createAgency(Agency agency) throws DietiEstateException {


        String Query="INSERT INTO agenziaimmobiliare(PartitaIVA, Nome, Sede) VALUES(?,?,?)";

        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, agency.getCodice_partitaIVA());
            stmt.setString(2, agency.getNome());
            stmt.setString(3, agency.getSede());
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }
}
