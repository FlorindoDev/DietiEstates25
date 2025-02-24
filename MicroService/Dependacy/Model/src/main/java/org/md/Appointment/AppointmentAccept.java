package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppointmentAccept extends Appointment{

    @JsonIgnore
    public final String name = "Accetata";
    public AppointmentAccept() {
    }

    public AppointmentAccept(Builder builder) {
        super(builder);
    }

    @JsonIgnore

    public String getName() {
        return name;
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
