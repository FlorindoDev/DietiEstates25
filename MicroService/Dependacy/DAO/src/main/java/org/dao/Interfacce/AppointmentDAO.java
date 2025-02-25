package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentPending;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

import java.util.ArrayList;

public interface AppointmentDAO {

    ArrayList<Appointment> getAllAppointment(Agent agent) throws DietiEstateException;

    ArrayList<Appointment> getAllAppointment(Acquirente acquirente) throws DietiEstateException;

    void changeStatusAppointment(Appointment appointment) throws DietiEstateException;

    void createAppointment(Appointment appointment) throws DietiEstateException;

    boolean hasUserAppointment(Appointment appointment) throws DietiEstateException;


}
