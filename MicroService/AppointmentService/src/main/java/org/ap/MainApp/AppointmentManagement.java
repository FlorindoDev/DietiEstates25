package org.ap.MainApp;

import org.ap.MainApp.interfacce.AppointmentService;
import org.ap.Validator.Interfacce.Validator;
import org.ap.Validator.Validate;
import org.dao.Interfacce.AppointmentDAO;
import org.dao.postgre.AppointmentPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentPending;
import org.md.Appointment.AppointmentReject;
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
    public String acceptAppointment(AppointmentAccept appointment) {
        return "";
    }

    @Override
    public String declineAppointment(AppointmentReject appointment) {
        //TODO METTERE NELLA CODA DI MESSAGGI LA NOTIFICA

        try {
            appointmentDAO.changeStatusAppointment(appointment);
            return "{\"code\": 0, \"message\": \"success of action decline appointment\"}";

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    @Override
    public String makeAppointment(Appointment appointment) {

        try {
            //TODO METTERE NELLA CODA DI MESSAGGI LA NOTIFICA
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
