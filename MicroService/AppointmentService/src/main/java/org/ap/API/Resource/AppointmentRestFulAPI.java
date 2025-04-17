package org.ap.API.Resource;

import jakarta.ws.rs.*;
import org.ap.API.interfacce.AppointmentAPI;
import org.ap.MainApp.AppointmentManagement;
import org.ap.MainApp.Main;
import org.ap.MainApp.interfacce.AppointmentService;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentReject;


import jakarta.ws.rs.core.MediaType;

@Path("/api")
public class AppointmentRestFulAPI implements AppointmentAPI {

    public static final String ERROR_EMAIL_PARAMETER_IS_REQUIRED = "{\"code\":-1, \"error\": \"email parameter is required\"}";

    public static final String ERROR_IDAPPOINTMENT_PARAMETER_IS_REQUIRED = "{\"code\":-1, \"error\": \"idAppointment parameter is required\"}";
    private final AppointmentService appointmentmanagement = new AppointmentManagement(Main.rabbitMQ);


    @Override
    @GET
    @Path("/appointments")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAppointment(@BeanParam AppointmentQuery query) {

        if(query.getIdAppointment() == 0){
            return ERROR_IDAPPOINTMENT_PARAMETER_IS_REQUIRED;
        }

        String result = appointmentmanagement.getAppointment(query);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @GET
    @Path("/appointments/agent")
    @Produces(MediaType.TEXT_PLAIN)
    public String loadAppointmentAgent(@BeanParam AppointmentQuery query) {

        String email = query.getEmail();
        if(email == null || email.isEmpty()) {
            return ERROR_EMAIL_PARAMETER_IS_REQUIRED;
        }

        String result = appointmentmanagement.loadAppointmentAgent(query);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @GET
    @Path("/appointments/acquirente")
    @Produces(MediaType.TEXT_PLAIN)
    public String loadAppointmentAcquirente(@BeanParam AppointmentQuery query) {

        String email = query.getEmail();
        if(email == null || email.isEmpty()) {
            return ERROR_EMAIL_PARAMETER_IS_REQUIRED;
        }

        String result = appointmentmanagement.loadAppointmentAcquirente(query);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @POST
    @Path("/appointments/acceptAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String acceptAppointment(AppointmentAccept appointment) {
        String result = appointmentmanagement.acceptAppointment(appointment);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @POST
    @Path("/appointments/declineAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String declineAppointment(AppointmentReject appointment) {
        String result = appointmentmanagement.declineAppointment(appointment);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @POST
    @Path("/appointments/makeAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String makeAppointment(Appointment appointment) {
        String result = appointmentmanagement.makeAppointment(appointment);
        appointmentmanagement.close();
        return result;
    }
}
