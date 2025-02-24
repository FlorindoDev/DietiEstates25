package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.Interfacce.AgentDAO;
import org.dao.Interfacce.AppointmentDAO;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.UpdateAppointmentFail;
import org.exc.DataBaseException.UserAppointmentAlreadyExists;
import org.exc.DietiEstateException;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentPending;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AppointmentPostgreDAO implements AppointmentDAO {

    CommunicationWithPostgre connection;

    private final String errorQuery = "[-] Error executing query: ";

    private static final Logger logger = Logger.getLogger(AppointmentPostgreDAO.class.getName());

    public AppointmentPostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public ArrayList<Appointment> getAppointment(Utente user) throws DietiEstateException {
        return null;
    }

    @Override
    public void changeStatusAppointment(Appointment appointment) throws DietiEstateException {

        Acquirente acquirente = getAcquirente(appointment);

        String Query = "UPDATE appuntamento SET esito = ? where idimmobile = ? and idacquirente = ? and data = ?";

        try{

            PreparedStatement stmt = connection.getStatment(Query);
            stmt.setString(1, appointment.getName());
            stmt.setInt(2, appointment.getEstate().getId_estate());
            stmt.setInt(3, acquirente.getId_user());
            stmt.setDate(4, Date.valueOf(appointment.getData()));

            if(connection.makeQueryUpdate(stmt) == 0) throw new UpdateAppointmentFail("Appointment not found or some thing else");

        }catch (SQLException e){
            logger.severe(errorQuery + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public void createAppointment(Appointment appointment) throws DietiEstateException {

        Acquirente acquirente = getAcquirente(appointment);

        String Query = "INSERT INTO appuntamento(esito,data,idacquirente,idimmobile) VALUES (?,?,?,?)";

        try{

            PreparedStatement stmt = connection.getStatment(Query);
            stmt.setString(1, "Da decidere");
            stmt.setDate(2, Date.valueOf(appointment.getData()));
            stmt.setInt(3, acquirente.getId_user());
            stmt.setInt(4, appointment.getEstate().getId_estate());

            connection.makeQueryUpdate(stmt);

        }catch (Exception e){
            logger.severe(errorQuery + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    @Override
    public boolean hasUserAppointment(Appointment appointment) throws DietiEstateException {

        Acquirente acquirente = getAcquirente(appointment);

        String Query = "SELECT idappuntamento FROM (appuntamento join immobile on immobile.idimmobile = appuntamento.idimmobile)" +
                " WHERE idacquirente = ? and data = ?";

        try{

            PreparedStatement stmt = connection.getStatment(Query);
            stmt.setInt(1,acquirente.getId_user());
            stmt.setDate(2, Date.valueOf(appointment.getData()));

            connection.makeQuery(stmt);

            if(connection.hasNextRow()) throw new UserAppointmentAlreadyExists();

            return true;

        }catch (SQLException e){
            logger.severe(errorQuery + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    private Acquirente getAcquirente(Appointment appointment) throws DietiEstateException {

        AcquirenteDAO take_acquirente = new AcquirentePostgreDAO();

        String email_acquirente = appointment.getAcquirente().getEmail();

        Acquirente acquirente = new Acquirente.Builder(0,email_acquirente)
                .build();

        acquirente = take_acquirente.getUser(acquirente);
        return acquirente;
    }


}
