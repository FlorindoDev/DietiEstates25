package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.EstateDAO;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

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
    public Agent getAgent(Estate agency) {
        return null;
    }

    //    @Override
//    public ArrayList<Estate> getEstate(Agency agency) throws DietiEstateException {
//
//        String query = "SELECT * FROM Immobile where partitaiva = ?";
//
//        PreparedStatement stmt = connection.getStatment(query);
//
//        try {
//            stmt.setString(1, agency.getCodicePartitaIVA());
//        } catch (SQLException e){
//            logger.severe("Error executing query: " + e.getMessage());
//            throw new ErrorCreateStatment();
//        }
//
//        try {
//            connection.makeQuery(stmt);
//        }catch (SQLException e){
//            logger.severe("Error executing query: " + e.getMessage());
//            throw new ErrorExecutingQuery();
//        }
//
//        ArrayList<Estate> estateList= new ArrayList<Estate>();
//        try {
//            while(connection.hasNextRow()){
//                connection.nextRow();
//                System.out.println("idimmobile: " + connection.extractInt("idimmobile"));
//                System.out.println("idagente: " + connection.extractString("idagente"));
//                System.out.println("idindirizzo: " + connection.extractString("idindirizzo"));
//                System.out.println("partitiaiva: " + connection.extractString("partitiaiva"));
//                System.out.println("foto: " + connection.extractString("foto"));
//                System.out.println("descrizione: " + connection.extractString("descrizione"));
//                System.out.println("prezzo: " + connection.extractString("prezzo"));
//                System.out.println("dimensioni: " + connection.extractString("dimensioni"));
//                System.out.println("stanze: " + connection.extractString("stanze"));
//                System.out.println("piano: " + connection.extractString("piano"));
//                System.out.println("bagni: " + connection.extractString("bagni"));
//                System.out.println("garage: " + connection.extractString("garage"));
//                System.out.println("ascensore: " + connection.extractString("ascensore"));
//                System.out.println("classeenergetica: " + connection.extractString("classeenergetica"));
//                System.out.println("modalita: " + connection.extractString("modalita"));
//                System.out.println("stato: " + connection.extractString("stato"));
//                System.out.println(); // Per separare le righe
//
//                Estate estate = new Estate.Builder<>(connection.extractInt("idimmobile"))
//                        .setAgente(new Agent.Builder<>()
//                                connection.extractString("idagente"))
//                        .setIndirizzo(connection.extractString("idindirizzo"))
//                        .setPartitaIva(connection.extractString("partitiaiva"))
//                        .setFoto(connection.extractString("foto"))
//                        .setDescrizione(connection.extractString("descrizione"))
//                        .setPrezzo(connection.extractString("prezzo"))
//                        .setDimensioni(connection.extractString("dimensioni"))
//                        .setStanze(connection.extractString("stanze"))
//                        .setPiano(connection.extractString("piano"))
//                        .setBagni(connection.extractString("bagni"))
//                        .setGarage(connection.extractString("garage"))
//                        .setAscensore(connection.extractString("ascensore"))
//                        .setClasseEnergetica(connection.extractString("classeenergetica"))
//                        .setModalita(connection.extractString("modalita"))
//                        .setStato(connection.extractString("stato"))
//                        .build();
//
//                estateList.add(estate);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }

}