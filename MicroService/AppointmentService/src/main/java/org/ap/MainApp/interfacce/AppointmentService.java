package org.ap.MainApp.interfacce;

import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentPending;
import org.md.Appointment.AppointmentReject;
import org.md.Utente.Utente;

import java.util.ArrayList;

public interface AppointmentService {
    ArrayList<Appointment> loadAppointment(Utente user);
    String acceptAppointment(AppointmentAccept appointment);
    String declineAppointment(AppointmentReject appointment);
    String makeAppointment(Appointment appointment);
}
