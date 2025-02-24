package org.ap.API.Resource;

import org.ap.API.interfacce.AppointmentAPI;
import org.ap.MainApp.AppointmentManagement;
import org.ap.MainApp.interfacce.AppointmentService;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentPending;
import org.md.Appointment.AppointmentReject;
import org.md.Utente.Utente;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("appointment")
public class AppointmentRestFulAPI implements AppointmentAPI {

    AppointmentService appointmentmanagement = new AppointmentManagement();

    @Override
    @POST
    @Path("loadAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String loadAppointment(Utente user) {
        return null;
    }

    @Override
    @POST
    @Path("acceptAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String acceptAppointment(AppointmentAccept appointment) {
        return null;
    }

    @Override
    @POST
    @Path("declineAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String declineAppointment(AppointmentReject appointment) {
        return appointmentmanagement.declineAppointment(appointment);
    }

    @Override
    @POST
    @Path("makeAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String makeAppointment(Appointment appointment) {

        return appointmentmanagement.makeAppointment(appointment);
    }
}
