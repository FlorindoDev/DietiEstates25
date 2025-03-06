package org.ap.API.Resource;

import org.ap.API.interfacce.AppointmentAPI;
import org.ap.MainApp.AppointmentManagement;
import org.ap.MainApp.Main;
import org.ap.MainApp.interfacce.AppointmentService;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentReject;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("appointment")
public class AppointmentRestFulAPI implements AppointmentAPI {

    private final AppointmentService appointmentmanagement = new AppointmentManagement(Main.rabbitMQ);

    @Override
    @POST
    @Path("loadAppointmentAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String loadAppointment(Agent agent) {
        String result = appointmentmanagement.loadAppointment(agent);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @POST
    @Path("loadAppointmentAcquirente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String loadAppointment(Acquirente acquirente) {
        String result = appointmentmanagement.loadAppointment(acquirente);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @POST
    @Path("acceptAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String acceptAppointment(AppointmentAccept appointment) {
        String result = appointmentmanagement.acceptAppointment(appointment);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @POST
    @Path("declineAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String declineAppointment(AppointmentReject appointment) {
        String result = appointmentmanagement.declineAppointment(appointment);
        appointmentmanagement.close();
        return result;
    }

    @Override
    @POST
    @Path("makeAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String makeAppointment(Appointment appointment) {
        String result = appointmentmanagement.makeAppointment(appointment);
        appointmentmanagement.close();
        return result;
    }
}
