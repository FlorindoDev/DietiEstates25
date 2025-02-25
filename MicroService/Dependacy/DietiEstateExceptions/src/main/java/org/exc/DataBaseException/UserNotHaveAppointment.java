package org.exc.DataBaseException;

public class UserNotHaveAppointment extends DietiEstateDBexception{
    public UserNotHaveAppointment() {
        super("{\"code\": 9 , \"message\": \" User Not Have Appointment\"}");
    }
}
