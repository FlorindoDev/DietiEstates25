package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AgencyDAO;
import org.exc.DataBaseException.*;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.ClasseEnergetica.ConverterEnergeticClass;
import org.md.Estate.ClasseEnergetica.EnergeticClass;
import org.md.Estate.Estate;
import org.md.Estate.Indirizzo;
import org.md.Estate.ClasseEnergetica.Mode.ConverterMode;
import org.md.Estate.ClasseEnergetica.Mode.Mode;
import org.md.Estate.Status.ConverterStatus;
import org.md.Estate.Status.Status;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AgencyPostgreDAO implements AgencyDAO {

    public static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";
    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(AgencyPostgreDAO.class.getName());

    protected PreparedStatement prepareStatementGetAgency(Agency agency, String query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, agency.getCodicePartitaIVA());
        } catch (Exception e) {
            logger.severe("Error executing query: " + e.getMessage());
            throw new ErrorCreateStatment();
        }

        return stmt;

    }

    protected PreparedStatement prepareStatementGetAgencyForName(Agency agency, String query) throws DietiEstateException {
        PreparedStatement stmt = connection.getStatment(query);

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


        String query="INSERT INTO agenziaimmobiliare(PartitaIVA, Nome, Sede) VALUES(?,?,?)";

        PreparedStatement stmt = connection.getStatment(query);

        try {
            stmt.setString(1, agency.getCodicePartitaIVA());
            stmt.setString(2, agency.getNome());
            stmt.setString(3, agency.getSede());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    @Override
    public boolean isAgencyAbsent(Agency agency) throws DietiEstateException {

        String query = "SELECT * FROM agenziaimmobiliare where partitaIVA = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) throw new AgencyAlreadyExists();
            return true;
        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    public boolean isAgencyPresent(Agency agency) throws DietiEstateException {

        String query = "SELECT * FROM agenziaimmobiliare where partitaIVA = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new AgencyNotExists();
            return true;
        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    @Override
    public boolean isNameAgencyAbsent(Agency agency) throws DietiEstateException {

        String query = "SELECT * FROM agenziaimmobiliare where nome = ?";

        PreparedStatement stmt = prepareStatementGetAgencyForName(agency,query);

        try {
            connection.makeQuery(stmt);
            if(connection.hasNextRow()) throw new AgencyNameAlreadyExists();
            return true;
        }catch(SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public ArrayList<Admin> getAdmins(Agency agency) throws DietiEstateException {

        isAgencyPresent(agency);

        String query = "SELECT *" +
                " FROM amministratore INNER JOIN agenziaimmobiliare ON amministratore.partitaiva = agenziaimmobiliare.partitaiva" +
                " WHERE amministratore.partitaiva = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);



        try {
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()){
                throw new Exception();
            }
            ArrayList<Admin> admins = new ArrayList<>();

            do{
                connection.nextRow();

                Agency fullAgency = new Agency.Builder<>(connection.extractString("partitaiva"))
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
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }



    }

    @Override
    public ArrayList<Agent> getAgents(Agency agency) throws DietiEstateException {
        isAgencyPresent(agency);

        String query = "SELECT *" +
                " FROM agenteimmobiliare INNER JOIN agenziaimmobiliare ON agenteimmobiliare.partitaiva = agenziaimmobiliare.partitaiva" +
                " WHERE agenteimmobiliare.partitaiva = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);



        try {
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()){
                throw new Exception();
            }
            ArrayList<Agent> agents = new ArrayList<>();

            do{
                connection.nextRow();

                Agency fullAgency = new Agency.Builder<>(connection.extractString("partitaiva"))
                        .setNome(connection.extractString("nome"))
                        .setSede(connection.extractString("sede"))
                        .setEmail(agency.getEmail())
                        .build();

                Agent agent = new Agent.Builder(1,connection.extractString("email"))
                        .setName(connection.extractString("nome"))
                        .setCognome(connection.extractString("cognome"))
                        .setAgency(fullAgency)
                        .build();

                agents.add(agent);


            }while(connection.hasNextRow());

            return agents;

        }catch(Exception e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }


    }

    @Override
    public ArrayList<Estate> getEstates(Agency agency) throws DietiEstateException {
        isAgencyPresent(agency);

        String query = "SELECT *" +
                " FROM immobile INNER JOIN agenziaimmobiliare ON immobile.partitaiva = agenziaimmobiliare.partitaiva" +
                " WHERE immobile.partitaiva = ?";

        PreparedStatement stmt = prepareStatementGetAgency(agency,query);



        try {
            connection.makeQuery(stmt);

            if(!connection.hasNextRow()){
                throw new Exception();
            }
            ArrayList<Estate> estates = new ArrayList<>();

            do{
                connection.nextRow();

                Agency fullAgency = new Agency.Builder<>(connection.extractString("partitaiva"))
                        .setNome(connection.extractString("nome"))
                        .setSede(connection.extractString("sede"))
                        .setEmail(agency.getEmail())
                        .build();

                Estate estate = new Estate.Builder<>(connection.extractInt("idimmobile"))
                        .setElevator(connection.extractBoolean("ascensore"))
                        .setAgente(null)
                        .setClasseEnergetica(null)
                        .setDescrizione(connection.extractString("descrizione"))
                        .setFloor(connection.extractInt("piano"))
                        .setFoto(connection.extractString("foto"))
                        .setGarage(connection.extractInt("garage"))
                        .setMode(null)
                        .setIndirizzo(null)
                        .setRooms(connection.extractInt("stanze"))
                        .setPrice(connection.extractInt("prezzo"))
                        .setWc(connection.extractInt("bagni"))
                        .setSpace(connection.extractInt("dimensioni"))
                        .setStato(null)
                        .setAgenzia(fullAgency)
                        .build();

                Agent agente = getAgentFromId(connection.extractString("idagente"));
                EnergeticClass classe = ConverterEnergeticClass.traslateFromString(connection.extractString("classeenergetica"));
                Mode mode = ConverterMode.traslateFromString(connection.extractString("modalita"));
                Status status = ConverterStatus.traslateFromString(connection.extractString("stato"));
                Indirizzo indirizzo = new IndirizzoPostgreDAO().getIndirizzoFromId(connection.extractString("idindirizzo"));

                estate.setAgente(agente);
                estate.setClasseEnergetica(classe);
                estate.setMode(mode);
                estate.setStato(status);
                estate.setIndirizzo(indirizzo);


                estates.add(estate);


            }while(connection.hasNextRow());

            return estates;

        }catch(Exception e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }

    private Agent getAgentFromId(String idagente) {
        return null;
    }


}
