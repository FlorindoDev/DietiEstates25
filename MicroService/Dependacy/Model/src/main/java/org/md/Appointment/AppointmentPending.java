package org.md.Appointment;

public class AppointmentPending extends Appointment{

    public AppointmentPending() {}

    public AppointmentPending(Builder builder) {
        super(builder);
    }

    public static class Builder  extends Appointment.Builder<AppointmentPending.Builder>{
        public Builder(int id_appointment) {
            super(id_appointment);
        }

        @Override
        public AppointmentPending build() {
            return new AppointmentPending(this);
        }
    }
}
