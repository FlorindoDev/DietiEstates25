package org.ap.API.interfacce;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.core.Response;
import org.ap.API.Resource.AppointmentQuery;
import org.ap.API.Resource.MeteoQuery;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentReject;


public interface AppointmentAPI {

    String getAppointment(@BeanParam AppointmentQuery query);
    String loadAppointmentAcquirente(@BeanParam AppointmentQuery query);
    String loadAppointmentAgent(@BeanParam AppointmentQuery query);
    String acceptAppointment(AppointmentAccept appointment);
    String declineAppointment(AppointmentReject appointment);
    String makeAppointment(Appointment appointment);
    Response meteo(@BeanParam MeteoQuery query);

}
