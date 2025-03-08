package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.Interfacce.Factory.QueryParametersNotify;
import org.dao.Interfacce.NotifyDAO;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UserNotifyNotFound;
import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Notify.Notify;
import org.md.Utente.Acquirente;

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

            if (parameters.isOrder()){
                query = query + "DESC";
            }else{
                query = query + "ASC";
            }

            acquirente = getAcquirente(parameters.getEmail());

            PreparedStatement stmt = connection.getStatment(query);

            stmt.setInt(1, acquirente.getIdUser());


            executeQuery(stmt, notifies, acquirente);


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

    @Override
    public List<Notify> getNotifyAcquirenteNoFilter(Acquirente acquirente) throws DietiEstateException {

        ArrayList<Notify> notifies = new ArrayList<>();

        String query = "SELECT * FROM notifica where idacquirente = ?";


        try {

            acquirente = getAcquirente(acquirente.getEmail());

            PreparedStatement stmt = connection.getStatment(query);

            stmt.setInt(1,acquirente.getIdUser());

            executeQuery(stmt, notifies, acquirente);


            return notifies;

        }catch (SQLException e){
            throw new ErrorExecutingQuery();
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
        if(connection !=null) {
            connection.close();
        }
    }
}
