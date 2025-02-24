package org.exc.DataBaseException;

public class UpdateAppointmentFail extends DietiEstateDBexception{
    public UpdateAppointmentFail() {
        super("{\"code\": 8 , \"message\": \" Error udpate appointment\"}");
    }
}
