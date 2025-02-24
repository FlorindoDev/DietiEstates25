package org.ap.MainApp.interfacce;

import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentPending;
import org.md.Utente.Utente;

import java.util.ArrayList;

public interface AppointmentService {
    ArrayList<Appointment> loadAppointment(Utente user);
    String acceptAppointment(AppointmentPending appointment);
    String declineAppointment(AppointmentPending appointment);
    String makeAppointment(Appointment appointment);
}
