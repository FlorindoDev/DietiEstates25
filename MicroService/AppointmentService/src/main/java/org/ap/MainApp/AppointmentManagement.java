package org.ap.MainApp;

import org.ap.MainApp.interfacce.AppointmentService;
import org.ap.Validator.Interfacce.Validator;
import org.ap.Validator.Validate;
import org.dao.Interfacce.AppointmentDAO;
import org.dao.postgre.AppointmentPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentPending;
import org.md.Utente.Utente;

import java.util.ArrayList;

public class AppointmentManagement implements AppointmentService {

    //ManagmentMQ message_queue;

    AppointmentDAO appointmentDAO = new AppointmentPostgreDAO();

    @Override
    public ArrayList<Appointment> loadAppointment(Utente user) {
        return null;
    }

    @Override
    public String acceptAppointment(AppointmentPending appointment) {
        return "";
    }

    @Override
    public String declineAppointment(AppointmentPending appointment) {
        return "";
    }

    @Override
    public String makeAppointment(Appointment appointment) {
        //TODO RICORDATI DI  METTERE CONTROLO PER LA DATA E IMMOBILE
        // (non si puo avere due apptamenti per lo stesso immobile nello stesso giorno)

        try {
            Validator validate = new Validate();
            validate.validateDate(appointment.getData());

            appointmentDAO.hasUserAppointment(appointment);
            appointmentDAO.createAppointment(appointment);
            return "{\"code\": 0, \"message\": \"success of action add appointment\"}";

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }
}
