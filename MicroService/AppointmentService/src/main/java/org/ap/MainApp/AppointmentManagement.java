package org.ap.MainApp;

import org.ap.MainApp.interfacce.AppointmentService;
import org.dao.Interfacce.EstateDAO;
import org.dao.Interfacce.Factory.QueryFactoryAppointment;
import org.dao.Interfacce.Factory.QueryParametersAppointment;
import org.dto.AppointmentSpecification;
import org.dao.postgre.EstatePostgreDAO;
import org.dao.postgre.Factory.FactoryFilteredQueryAppointmentPostgres;
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
import org.va.Validator;

import java.util.ArrayList;

public class AppointmentManagement implements AppointmentService {

    private AppointmentDAO appointmentDAO;

    private final NotifyAppointmentFactory factory;

    private final ManagementSenderNotifyMQ senderMQ;

    private EstateDAO estateDAO;

    public AppointmentManagement(ApplicationContext rabbitMQ) {
        senderMQ = rabbitMQ.getBean(ManagementSenderNotifyRabbitMQ.class);
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
    public String loadAppointmentAcquirente(QueryParametersAppointment parameters) {

        appointmentDAO = new AppointmentPostgreDAO();

        QueryFactoryAppointment factoryAppointment = new FactoryFilteredQueryAppointmentPostgres();

        try {

            String query = factoryAppointment.selectQueryAcquirenteAllColumns(parameters);

            ArrayList<Appointment> appointments = appointmentDAO.getAllAppointmentAcquirente(query, parameters);

            return ConvertListAppointmentToJson(appointments);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    public String getAppointment(QueryParametersAppointment parameters){

        appointmentDAO = new AppointmentPostgreDAO();

        QueryFactoryAppointment factoryAppointment = new FactoryFilteredQueryAppointmentPostgres();

        try{
            String query = factoryAppointment.selectSpecificAppointment(parameters);
            AppointmentSpecification appointment =  appointmentDAO.getAppointment(query, parameters);



            return "{\"code\": 0, \"message\": \"success of action accept appointment\", \"Appointment\":" + appointment.TranslateToJson() + "}";

        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }


    @Override
    public String loadAppointmentAgent(QueryParametersAppointment parameters) {

        appointmentDAO = new AppointmentPostgreDAO();

        QueryFactoryAppointment factoryAppointment = new FactoryFilteredQueryAppointmentPostgres();

        try {

            String query = factoryAppointment.selectQueryAgentAllColumns(parameters);

            ArrayList<Appointment> appointments = appointmentDAO.getAllAppointmentAgent(query, parameters);

            return ConvertListAppointmentToJson(appointments);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }
    }

    @Override
    public String acceptAppointment(AppointmentAccept appointment) {

        appointmentDAO = new AppointmentPostgreDAO();

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

        appointmentDAO = new AppointmentPostgreDAO();

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

        appointmentDAO = new AppointmentPostgreDAO();
        estateDAO = new EstatePostgreDAO();

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
        if(appointmentDAO != null)appointmentDAO.close();
        if(estateDAO != null)estateDAO.close();
    }
}
