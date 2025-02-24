package org.md.Appointment;

public class AppointmentReject extends Appointment{

    public AppointmentReject() {
    }


    public AppointmentReject(Builder builder) {
        super(builder);
    }

    public static class Builder  extends Appointment.Builder<AppointmentReject.Builder>{
        public Builder(int id_appointment) {
            super(id_appointment);
        }

        @Override
        public AppointmentReject build() {
            return new AppointmentReject(this);
        }
    }
}
