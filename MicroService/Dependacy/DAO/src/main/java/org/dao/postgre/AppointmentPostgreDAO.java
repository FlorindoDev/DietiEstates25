package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.Interfacce.AppointmentDAO;
import org.dao.Interfacce.Factory.QueryParametersAppointment;
import org.dao.Interfacce.UtenteDAO;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UpdateAppointmentFail;
import org.exc.DataBaseException.UserAppointmentAlreadyExists;
import org.exc.DataBaseException.UserNotHaveAppointment;
import org.exc.DietiEstateException;
import org.md.Appointment.Appointment;
import org.md.Estate.Estate;
import org.md.Utente.Acquirente;
import org.md.Utente.Utente;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AppointmentPostgreDAO implements AppointmentDAO {

    CommunicationWithPostgre connection;

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";

    private static final Logger logger = Logger.getLogger(AppointmentPostgreDAO.class.getName());

    public AppointmentPostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public ArrayList<Appointment> getAllAppointmentAgent(String query, QueryParametersAppointment parameters) throws DietiEstateException {

        ArrayList<Appointment> appointments = new ArrayList<>();

        UtenteDAO utenteDAO = new UtentePostgreDAO(connection);

        Utente user  = utenteDAO.getUserFromEmail(parameters.getEmail());

        if (parameters.isOrder()){
            query = query + "DESC";
        }else{
            query = query + "ASC";
        }


        try{

            PreparedStatement stmt = connection.getStatment(query);
            stmt.setInt(1,user.getIdUser());
            connection.makeQuery(stmt);

        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        try {
            
            if(!connection.hasNextRow()) throw new UserNotHaveAppointment();
            while (connection.hasNextRow()) {

                connection.nextRow();

                Estate estate = new Estate.Builder<>(connection.extractInt("idimmobile")).build();

                int idacquirente = connection.extractInt("idacquirente");
                String emailacquirente = connection.extractString("email_acquirente");

                Acquirente acquirente = new Acquirente.Builder(idacquirente,emailacquirente)
                        .build();

                Appointment appointment = new Appointment.Builder<>(connection.extractInt("idappuntamento"))
                        .setEstate(estate)
                        .setData(String.valueOf(connection.extractDate("data")))
                        .setdataRichesta(String.valueOf(connection.extractDate("datarichiesta")))
                        .setEsito(connection.extractString("esito"))
                        .setAcquirente(acquirente)
                        .build();

                appointments.add(appointment);
            }
        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
        return appointments;
    }

    @Override
    public ArrayList<Appointment> getAllAppointmentAcquirente(String query, QueryParametersAppointment parameters) throws DietiEstateException {

        ArrayList<Appointment> appointments = new ArrayList<>();

        UtenteDAO utenteDAO = new UtentePostgreDAO(connection);

        Utente user  = utenteDAO.getUserFromEmail(parameters.getEmail());

        if (parameters.isOrder()){
            query = query + "DESC";
        }else{
            query = query + "ASC";
        }

        try{

            PreparedStatement stmt = connection.getStatment(query);
            stmt.setInt(1,user.getIdUser());
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new UserNotHaveAppointment();

        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        try {


            while (connection.hasNextRow()) {

                connection.nextRow();

                Estate estate = new Estate.Builder<>(connection.extractInt("idimmobile")).build();

                Appointment appointment = new Appointment.Builder<>(connection.extractInt("idappuntamento"))
                        .setEstate(estate)
                        .setData(String.valueOf(connection.extractDate("data")))
                        .setdataRichesta(String.valueOf(connection.extractDate("datarichiesta")))
                        .setEsito(connection.extractString("esito"))
                        .setAcquirente((Acquirente) user)
                        .build();

                appointments.add(appointment);
            }
        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
        return appointments;
    }

    @Override
    public void updateStatusAppointment(Appointment appointment) throws DietiEstateException {

        Acquirente acquirente = getAcquirente(appointment);

        String query = "UPDATE appuntamento SET esito = ? where idimmobile = ? and idacquirente = ? and data = ?";

        try{

            PreparedStatement stmt = connection.getStatment(query);
            stmt.setString(1, appointment.getName());
            stmt.setInt(2, appointment.getEstate().getIdEstate());
            stmt.setInt(3, acquirente.getIdUser());
            stmt.setDate(4, Date.valueOf(appointment.getData()));

            if(connection.makeQueryUpdate(stmt) == 0) throw new UpdateAppointmentFail("Appointment not found or some thing else");

        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public void createAppointment(Appointment appointment) throws DietiEstateException {

        Acquirente acquirente = getAcquirente(appointment);

        String query = "INSERT INTO appuntamento(esito,data,idacquirente,idimmobile,datarichiesta) VALUES (?,?,?,?,?)";

        try{

            PreparedStatement stmt = connection.getStatment(query);
            stmt.setString(1, "Da decidere");
            stmt.setDate(2, Date.valueOf(appointment.getData()));
            stmt.setInt(3, acquirente.getIdUser());
            stmt.setInt(4, appointment.getEstate().getIdEstate());
            stmt.setDate(5, Date.valueOf(LocalDate.now()));

            connection.makeQueryUpdate(stmt);

        }catch (Exception e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public boolean hasUserAppointment(Appointment appointment) throws DietiEstateException {

        Acquirente acquirente = getAcquirente(appointment);

        String query = "SELECT idappuntamento FROM (appuntamento join immobile on immobile.idimmobile = appuntamento.idimmobile)" +
                " WHERE idacquirente = ? and data = ?";

        try{

            PreparedStatement stmt = connection.getStatment(query);
            stmt.setInt(1,acquirente.getIdUser());
            stmt.setDate(2, Date.valueOf(appointment.getData()));

            connection.makeQuery(stmt);

            if(connection.hasNextRow()) throw new UserAppointmentAlreadyExists();

            return true;

        }catch (SQLException e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    private Acquirente getAcquirente(Appointment appointment) throws DietiEstateException {

        AcquirenteDAO takeAcquirente = new AcquirentePostgreDAO();

        String emailAcquirente = appointment.getAcquirente().getEmail();

        Acquirente acquirente = new Acquirente.Builder(0,emailAcquirente)
                .build();

        acquirente = takeAcquirente.getUser(acquirente);

        acquirente.setPassword(" ");

        return acquirente;
    }

    @Override
    public void close(){
        connection.close();
    }

}
