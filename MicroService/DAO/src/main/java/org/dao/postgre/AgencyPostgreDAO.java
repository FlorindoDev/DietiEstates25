package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AgencyDAO;
import org.exc.DataBaseException.*;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.md.Utente.Utente;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AgencyPostgreDAO implements AgencyDAO {

    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    protected PreparedStatement PrepareStatementGetAgency(Agency agency, String Query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, agency.getCodice_partitaIVA());
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;

    }

    protected PreparedStatement PrepareStatementGetAgencyForName(Agency agency, String Query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(Query);

        try {
            stmt.setString(1, agency.getNome());
        } catch (SQLException e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;

    }

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

    @Override
    public boolean isAgencyAbsent(Agency agency) throws DietiEstateException {

        String Query = "SELECT * FROM agenziaimmobiliare where partitaIVA = ?";

        PreparedStatement stmt = PrepareStatementGetAgency(agency,Query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) throw new AgencyAlreadyExists();
            return true;
        }catch(SQLException e){
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    public boolean isAgencyPresent(Agency agency) throws DietiEstateException {

        String Query = "SELECT * FROM agenziaimmobiliare where partitaIVA = ?";

        PreparedStatement stmt = PrepareStatementGetAgency(agency,Query);

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new AgencyNotExists();
            return true;
        }catch(SQLException e){
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    @Override
    public boolean isNameAgencyAbsent(Agency agency) throws DietiEstateException {

        String Query = "SELECT * FROM agenziaimmobiliare where nome = ?";

        PreparedStatement stmt = PrepareStatementGetAgencyForName(agency,Query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) throw new AgencyNameAlreadyExists();
            return true;
        }catch(SQLException e){
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public ArrayList<Admin> getAdmins(Agency agency) throws DietiEstateException {

        isAgencyPresent(agency);

        String Query = "SELECT *" +
                " FROM amministratore INNER JOIN agenziaimmobiliare ON amministratore.partitaiva = agenziaimmobiliare.partitaiva" +
                " WHERE amministratore.partitaiva = ?";

        PreparedStatement stmt = PrepareStatementGetAgency(agency,Query);



        try {
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()){
                throw new Exception();
            }
            ArrayList<Admin> admins = new ArrayList<Admin>();

            do{
                System.out.println(connection.extractString("email"));
                connection.nextRow();

                Agency fullAgency = new Agency.Builder(connection.extractString("partitaiva"))
                        .setNome(connection.extractString("nome"))
                        .setSede(connection.extractString("sede"))
                        .setEmail(agency.getEmail())
                        .build();

                Admin admin = new Admin.Builder(1,connection.extractString("email"))
                        .setName(connection.extractString("nome"))
                        .setCognome(connection.extractString("cognome"))
                        .setIsSupport(connection.extractBoolean("issupportoamministratore"))
                        .setAgency(fullAgency)
                        .build();

                admins.add(admin);


            }while(connection.hasNextRow());

            return admins;

        }catch(Exception e){
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }



    }


}
