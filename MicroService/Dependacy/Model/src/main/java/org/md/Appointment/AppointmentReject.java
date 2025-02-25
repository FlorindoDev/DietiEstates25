package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppointmentReject extends Appointment{

    @JsonIgnore
    private final String name = "rifiutata";

    public AppointmentReject() {
    }

    public AppointmentReject(Builder builder) {
        super(builder);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return name;
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
