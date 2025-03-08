package org.ap.API.interfacce;

import jakarta.ws.rs.BeanParam;
import org.ap.API.Resource.AppointmentQuery;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentReject;


public interface AppointmentAPI {
    String loadAppointmentAcquirente(@BeanParam AppointmentQuery query);
    String loadAppointmentAgent(@BeanParam AppointmentQuery query);
    String acceptAppointment(AppointmentAccept appointment);
    String declineAppointment(AppointmentReject appointment);
    String makeAppointment(Appointment appointment);

}
