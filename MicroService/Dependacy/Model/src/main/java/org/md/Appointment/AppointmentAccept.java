package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppointmentAccept extends Appointment{

    @JsonIgnore
    private final String name = "Accetata";
    public AppointmentAccept() {
    }

    public AppointmentAccept(Builder builder) {
        super(builder);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return name;
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
