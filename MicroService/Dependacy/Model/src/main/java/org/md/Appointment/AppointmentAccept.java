package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppointmentAccept extends Appointment{

    @JsonIgnore
    private static final String NAME = "Accetata";
    public AppointmentAccept() {
    }

    public AppointmentAccept(Builder builder) {
        super(builder);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return NAME;
    }


    public static class Builder  extends Appointment.Builder<AppointmentAccept.Builder>{
        public Builder(int idAppointment) {
            super(idAppointment);
        }

        @Override
        public AppointmentAccept build() {
            return new AppointmentAccept(this);
        }
    }
}
