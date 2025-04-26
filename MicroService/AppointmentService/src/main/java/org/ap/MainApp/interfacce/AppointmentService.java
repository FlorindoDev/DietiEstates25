package org.ap.MainApp.interfacce;

import org.dao.Interfacce.Factory.QueryParametersAppointment;
import org.md.Appointment.Appointment;
import org.md.Appointment.AppointmentAccept;
import org.md.Appointment.AppointmentReject;



public interface AppointmentService {
    String loadAppointmentAcquirente(QueryParametersAppointment parameters);
    String getAppointment(QueryParametersAppointment parameters);
    String loadAppointmentAgent(QueryParametersAppointment parameters);
    String acceptAppointment(AppointmentAccept appointment);
    String declineAppointment(AppointmentReject appointment);
    String makeAppointment(Appointment appointment);
    String getMeteo(String queryParameters);
    void close();
}
