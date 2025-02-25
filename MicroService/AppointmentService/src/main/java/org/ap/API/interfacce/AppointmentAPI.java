package org.ap.API.interfacce;

import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentPending;
import org.md.Appointment.AppointmentReject;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

public interface AppointmentAPI {
    String loadAppointment(Acquirente acquirente);
    String loadAppointment(Agent agent);
    String acceptAppointment(AppointmentAccept appointment);
    String declineAppointment(AppointmentReject appointment);
    String makeAppointment(Appointment appointment);

}
