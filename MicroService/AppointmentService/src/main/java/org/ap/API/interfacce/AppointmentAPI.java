package org.ap.API.interfacce;

import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentPending;
import org.md.Utente.Utente;

public interface AppointmentAPI {
    String loadAppointment(Utente user);
    String acceptAppointment(AppointmentPending appointment);
    String declineAppointment(AppointmentPending appointment);
    String makeAppointment(Appointment appointment);

}
