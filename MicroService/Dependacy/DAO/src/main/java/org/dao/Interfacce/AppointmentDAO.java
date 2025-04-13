package org.dao.Interfacce;

import org.dao.Interfacce.Factory.QueryParametersAppointment;
import org.exc.DietiEstateException;
import org.md.Appointment.Appointment;
import java.util.ArrayList;

public interface AppointmentDAO {

    ArrayList<Appointment> getAllAppointmentAgent(String query, QueryParametersAppointment parameters) throws DietiEstateException;

    public Appointment getAppointment() throws DietiEstateException;

    ArrayList<Appointment> getAllAppointmentAcquirente(String query, QueryParametersAppointment parameters) throws DietiEstateException;

    void updateStatusAppointment(Appointment appointment) throws DietiEstateException;

    void createAppointment(Appointment appointment) throws DietiEstateException;

    boolean hasUserAppointment(Appointment appointment) throws DietiEstateException;

    void close();
}
