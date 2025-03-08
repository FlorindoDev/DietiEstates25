package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppointmentReject extends Appointment{

    @JsonIgnore
    private static final String NAME = "Rifiutato";

    public AppointmentReject() {
    }

    public AppointmentReject(Builder builder) {
        super(builder);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return NAME;
    }

    public static class Builder  extends Appointment.Builder<AppointmentReject.Builder>{
        public Builder(int idAppointment) {
            super(idAppointment);
        }

        @Override
        public AppointmentReject build() {
            return new AppointmentReject(this);
        }
    }
}
