package org.ap.API.interfacce;

import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentPending;
import org.md.Appointment.AppointmentReject;
import org.md.Utente.Utente;

public interface AppointmentAPI {
    String loadAppointment(Utente user);
    String acceptAppointment(AppointmentAccept appointment);
    String declineAppointment(AppointmentReject appointment);
    String makeAppointment(Appointment appointment);

}
