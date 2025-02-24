package org.exc.DataBaseException;

public class UserAppointmentAlreadyExists extends DietiEstateDBexception{
    public UserAppointmentAlreadyExists() {
        super("{\"code\": 7 , \"message\": \" User appointment Already Exists\"}");
    }
}
