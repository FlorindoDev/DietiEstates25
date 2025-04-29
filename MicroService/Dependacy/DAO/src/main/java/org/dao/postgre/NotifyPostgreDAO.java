package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.Interfacce.AgentDAO;
import org.dao.Interfacce.Factory.QueryParametersNotify;
import org.dao.Interfacce.NotifyDAO;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserNotifyNotFound;
import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Notify.Notify;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class NotifyPostgreDAO implements NotifyDAO {

    private final CommunicationWithPostgre connection;

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";

    private static final Logger logger = Logger.getLogger(NotifyPostgreDAO.class.getName());

    public NotifyPostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public void createNotify(Notify notify) throws DietiEstateException {

        Acquirente acquirente = getAcquirente(notify.getAcquirente().getEmail());

        String query = "INSERT INTO Notifica(tiponotifica,messaggio,data,dataricezione,idacquirente,idimmobile) VALUES (?::notificatipo,?,?,?,?,?)";

        try{
            PreparedStatement stmt = connection.getStatment(query);

            stmt.setString(1,notify.getTipo());
            stmt.setString(2,notify.getMessage());
            stmt.setDate(3,Date.valueOf(notify.getData()));
            stmt.setDate(4, Date.valueOf(LocalDate.now()));
            stmt.setInt(5,acquirente.getIdUser());
            stmt.setInt(6,notify.getEstate().getIdEstate());

            connection.makeQueryUpdate(stmt);

        }catch (Exception e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    public List<Notify> getNotifyAcquirenteAllFilter(String query, QueryParametersNotify parameters) throws DietiEstateException {

        ArrayList<Notify> notifies = new ArrayList<>();

        Acquirente acquirente;

        try {

            acquirente = getAcquirente(parameters.getEmail());

            PreparedStatement stmt = connection.getStatment(query);

            stmt.setInt(1, acquirente.getIdUser());


            executeQuery(stmt, notifies, acquirente);


            return notifies;

        }catch (SQLException e){
            throw new ErrorExecutingQuery();
        }


    }

    @Override
    public List<Notify> getAgentNotifyAcquirenteAllFilter(String query, QueryParametersNotify parameters) throws DietiEstateException {

        ArrayList<Notify> notifies = new ArrayList<>();

        AgentDAO agentDAO = new AgentPostgreDAO(connection);

        Agent agent = new Agent.Builder(0, parameters.getEmail()).build();
        List<Integer> idEstates = agentDAO.getIdEstate(agent);

        if(idEstates.size() == 0) throw new UserNotifyNotFound();

        PreparedStatement stmt = connection.getStatment(query);

        try {
            for(Integer id : idEstates){
                stmt.setInt(1, id);
                connection.makeQuery(stmt);

                if(!connection.hasNextRow()) throw  new UserNotifyNotFound();

                while(connection.hasNextRow()){
                    connection.nextRow();
                    Acquirente acquirente = new Acquirente.Builder(connection.extractInt("idacquirente"), "").build();
                    notifies.add(initNotify(acquirente));
                }

            }

            return notifies;
        }catch (SQLException e){
            throw new ErrorExecutingQuery();
        }
    }

    private void executeQuery(PreparedStatement stmt, ArrayList<Notify> notifies, Acquirente acquirente) throws SQLException, UserNotifyNotFound {
        connection.makeQuery(stmt);

        if(!connection.hasNextRow()) throw  new UserNotifyNotFound();

        while(connection.hasNextRow()){

            connection.nextRow();

            notifies.add(initNotify(acquirente));

        }
    }


    private Notify initNotify(Acquirente acquirente) {

        Estate estate = new Estate.Builder<>(connection.extractInt("idimmobile")).build();

        return new Notify.Builder<>(connection.extractString("messaggio"))
                .setIdNotify(connection.extractInt("idnotifica"))
                .setData(String.valueOf(connection.extractDate("data")))
                .setDataRicezione(String.valueOf(connection.extractDate("dataricezione")))
                .setTipo(connection.extractString("tiponotifica"))
                .setEstate(estate)
                .setUser(acquirente)
                .build();
    }

    private Acquirente getAcquirente(String emailAcquirente) throws DietiEstateException {

        AcquirenteDAO takeAcquirente = new AcquirentePostgreDAO(connection);


        Acquirente acquirente = new Acquirente.Builder(0,emailAcquirente)
                .build();

        acquirente = takeAcquirente.getUser(acquirente);

        acquirente.setPassword(" ");

        return acquirente;
    }


    @Override
    public void close(){
        if(connection != null) {
            connection.close();
        }
    }
}
