package org.md.Notify;

import org.md.Appointment.Appointment;
import org.md.Notify.Interfacce.NotifyAppointmentFactory;

public class NotifyBasicAppointmentFactory implements NotifyAppointmentFactory {

    @Override
    public Notify createRejectedNotify(Appointment appointment) {
        return new AppuntamentoRifiutato.Builder("Il tuo appuntamento è stato rifiutato")
                .setData(appointment.getData())
                .setEstate(appointment.getEstate())
                .setUser(appointment.getAcquirente())
                .build();
    }

    @Override
    public Notify createAcceptedNotify(Appointment appointment) {
        return new AppuntamentoAccettato.Builder("Il tuo appuntamento è stato accettato")
                .setData(appointment.getData())
                .setEstate(appointment.getEstate())
                .setUser(appointment.getAcquirente())
                .build();
    }

    @Override
    public Notify createPendingNotify(Appointment appointment) {
        return new AppuntamentoDaConfermare.Builder("Il tuo appuntamento deve essere accettato")
                .setData(appointment.getData())
                .setEstate(appointment.getEstate())
                .setUser(appointment.getAcquirente())
                .build();
    }
}
