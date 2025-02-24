package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentPending;
import org.md.Utente.Utente;

import java.util.ArrayList;

public interface AppointmentDAO {

    ArrayList<Appointment> getAppointment(Utente user) throws DietiEstateException;

    void changeStatusAppointment(AppointmentPending appointment) throws DietiEstateException;

    void createAppointment(Appointment appointment) throws DietiEstateException;


}
