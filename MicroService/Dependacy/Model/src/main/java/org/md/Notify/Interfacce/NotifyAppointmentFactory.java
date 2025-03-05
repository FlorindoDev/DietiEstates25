package org.md.Notify.Interfacce;

import org.md.Appointment.Appointment;
import org.md.Notify.Notify;

public interface NotifyAppointmentFactory {

    Notify createRejectedNotify(Appointment appointment);

    Notify createAcceptedNotify(Appointment appointment);

    Notify createPendingNotify(Appointment appointment);
}
