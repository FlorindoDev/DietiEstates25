package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppointmentPending extends Appointment{

    @JsonIgnore
    public final String name = "Da decidere";

    public AppointmentPending() {}

    public AppointmentPending(Builder builder) {
        super(builder);
    }

    @JsonIgnore
    public String getName() {
        return name;
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
