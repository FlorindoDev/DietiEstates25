package org.md.Appointment;

public class AppointmentAccept extends Appointment{

    public AppointmentAccept() {
    }

    public AppointmentAccept(Builder builder) {
        super(builder);
    }

    public static class Builder  extends Appointment.Builder<AppointmentAccept.Builder>{
        public Builder(int id_appointment) {
            super(id_appointment);
        }

        @Override
        public AppointmentAccept build() {
            return new AppointmentAccept(this);
        }
    }
}
