package org.ap.MainApp;

import org.ap.MainApp.interfacce.AppointmentService;
import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.md.Notify.Interfacce.NotifyAppointmentFactory;
import org.md.Notify.Notify;
import org.md.Notify.NotifyBasicAppointmentFactory;
import org.rab.Interfacce.ManagementSenderNotifyMQ;
import org.rab.Resource.Senders.ManagementSenderNotifyRabbitMQ;
import org.springframework.context.ApplicationContext;
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

    private final AppointmentDAO appointmentDAO;

    private final NotifyAppointmentFactory factory;

    private final ManagementSenderNotifyMQ senderMQ;

    private final EstateDAO estateDAO;


    public AppointmentManagement(ApplicationContext rabbitMQ) {
        senderMQ = rabbitMQ.getBean(ManagementSenderNotifyRabbitMQ.class);
        appointmentDAO = new AppointmentPostgreDAO();
        estateDAO = new EstatePostgreDAO();
        factory = new NotifyBasicAppointmentFactory();
    }

    private String ConvertListAppointmentToJson(ArrayList<Appointment> appointments) {
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

            return ConvertListAppointmentToJson(appointments);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    public String loadAppointment(Agent user) {
        try {

            ArrayList<Appointment> appointments = appointmentDAO.getAllAppointment(user);

            return ConvertListAppointmentToJson(appointments);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }
    }

    @Override
    public String acceptAppointment(AppointmentAccept appointment) {

        try {

            appointmentDAO.updateStatusAppointment(appointment);

            Notify notify = factory.createAcceptedNotify(appointment);

            senderMQ.enQueueAppointmentNotify(notify.TranslateToJson());

            return "{\"code\": 0, \"message\": \"success of action accept appointment\"}";

        } catch (DietiEstateException e) {
            return e.getMessage();
        }
    }

    @Override
    public String declineAppointment(AppointmentReject appointment) {

        try {

            appointmentDAO.updateStatusAppointment(appointment);

            Notify notify = factory.createRejectedNotify(appointment);

            senderMQ.enQueueAppointmentNotify(notify.TranslateToJson());

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

            estateDAO.isEstatePresent(appointment.getEstate());
            appointmentDAO.hasUserAppointment(appointment);
            appointmentDAO.createAppointment(appointment);

            Notify notify = factory.createPendingNotify(appointment);

            senderMQ.enQueueAppointmentNotify(notify.TranslateToJson());

            return "{\"code\": 0, \"message\": \"success of action add appointment\"}";

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    @Override
    public void close() {
        appointmentDAO.close();
        estateDAO.close();
    }
}
