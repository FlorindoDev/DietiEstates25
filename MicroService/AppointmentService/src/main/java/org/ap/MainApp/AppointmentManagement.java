package org.ap.MainApp;

import org.ap.MainApp.interfacce.AppointmentService;
import org.va.Validate;
import org.dao.Interfacce.AppointmentDAO;
import org.dao.postgre.AppointmentPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentReject;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;
import org.va.Validator;

import java.util.ArrayList;

public class AppointmentManagement implements AppointmentService {

    //ManagmentMQ message_queue;

    AppointmentDAO appointmentDAO = new AppointmentPostgreDAO();

    private String ConvertToJson(ArrayList<Appointment> appointments) {
        String json = "{\"code\": 0, \"message\": \"success of action get appointment\", \"Appointments\": [";

        for(Appointment appointment : appointments){
            json = json.concat(appointment.TranslateToJson());
            if(!appointment.equals(appointments.getLast()))
                json = json.concat(",");

        }

        return json + "]}";
    }


    @Override
    public String loadAppointment(Acquirente user) {
        try {

            ArrayList<Appointment> appointments = appointmentDAO.getAllAppointment(user);

            return ConvertToJson(appointments);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    public String loadAppointment(Agent user) {
        try {

            ArrayList<Appointment> appointments = appointmentDAO.getAllAppointment(user);

            return ConvertToJson(appointments);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }
    }

    @Override
    public String acceptAppointment(AppointmentAccept appointment) {
        //TODO METTERE NELLA CODA DI MESSAGGI LA NOTIFICA
        //TODO AGGIUNGERE DEI CONTROLLI PER VALORI NULL
        try {

            appointmentDAO.changeStatusAppointment(appointment);
            return "{\"code\": 0, \"message\": \"success of action accept appointment\"}";

        } catch (DietiEstateException e) {
            return e.getMessage();
        }
    }

    @Override
    public String declineAppointment(AppointmentReject appointment) {
        //TODO METTERE NELLA CODA DI MESSAGGI LA NOTIFICA
        //TODO AGGIUNGERE DEI CONTROLLI PER VALORI NULL
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
            Validator validate = Validate.getInstance();

            validate.validateDate(appointment.getData());

            appointmentDAO.hasUserAppointment(appointment);
            appointmentDAO.createAppointment(appointment);
            return "{\"code\": 0, \"message\": \"success of action add appointment\"}";

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }
}
