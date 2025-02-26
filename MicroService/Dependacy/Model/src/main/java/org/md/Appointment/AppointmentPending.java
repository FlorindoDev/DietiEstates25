package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppointmentPending extends Appointment{

    @JsonIgnore
    private static final String NAME = "Da decidere";

    public AppointmentPending() {}

    public AppointmentPending(Builder builder) {
        super(builder);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return NAME;
    }

    public static class Builder  extends Appointment.Builder<AppointmentPending.Builder>{
        public Builder(int idAppointment) {
            super(idAppointment);
        }

        @Override
        public AppointmentPending build() {
            return new AppointmentPending(this);
        }
    }
}
