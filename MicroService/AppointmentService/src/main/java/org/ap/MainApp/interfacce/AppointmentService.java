package org.ap.MainApp.interfacce;

import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentPending;
import org.md.Appointment.AppointmentReject;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

import java.util.ArrayList;

public interface AppointmentService {
    String loadAppointment(Acquirente user);
    String loadAppointment(Agent user);
    String acceptAppointment(AppointmentAccept appointment);
    String declineAppointment(AppointmentReject appointment);
    String makeAppointment(Appointment appointment);
}
