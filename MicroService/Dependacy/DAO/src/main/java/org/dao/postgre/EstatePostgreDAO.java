package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.EstateDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.Estate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class EstatePostgreDAO implements EstateDAO {

    private CommunicationWithPostgre connection;

    private static final Logger logger = Logger.getLogger(EstatePostgreDAO.class.getName());

    public EstatePostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public String createEstate(Estate newEstate) {
        return "";
    }

    @Override
    public String changeEstate(Estate changEstate) {
        return "";
    }

    @Override
    public ArrayList<Estate> getEstate(Agency agency) throws DietiEstateException {

        String query = "SELECT * FROM Immobile where idAgenzia = ?";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, agency.getCodice_partitaIVA());
        } catch (SQLException e){
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQuery(stmt);
        }catch (SQLException e){
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        try {
            while(connection.hasNextRow()){
                System.out.println("idimmobile: " + connection.extractString("idimmobile"));
                System.out.println("idagente: " + connection.extractString("idagente"));
                System.out.println("idindirizzo: " + connection.extractString("idindirizzo"));
                System.out.println("partitiaiva: " + connection.extractString("partitiaiva"));
                System.out.println("foto: " + connection.extractString("foto"));
                System.out.println("descrizione: " + connection.extractString("descrizione"));
                System.out.println("prezzo: " + connection.extractString("prezzo"));
                System.out.println("dimensioni: " + connection.extractString("dimensioni"));
                System.out.println("stanze: " + connection.extractString("stanze"));
                System.out.println("piano: " + connection.extractString("piano"));
                System.out.println("bagni: " + connection.extractString("bagni"));
                System.out.println("garage: " + connection.extractString("garage"));
                System.out.println("ascensore: " + connection.extractString("ascensore"));
                System.out.println("classeenergetica: " + connection.extractString("classeenergetica"));
                System.out.println("modalita: " + connection.extractString("modalita"));
                System.out.println("stato: " + connection.extractString("stato"));
                System.out.println(); // Per separare le righe
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

}