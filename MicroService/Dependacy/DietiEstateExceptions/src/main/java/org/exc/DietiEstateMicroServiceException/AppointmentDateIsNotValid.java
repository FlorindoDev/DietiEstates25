package org.exc.DietiEstateMicroServiceException;

public class AppointmentDateIsNotValid extends DietiEstateMicroSercviceException{
    public AppointmentDateIsNotValid() {
        super("{\"code\": 106 , \"message\": \" Date of appointment not valid\"}");
    }
}
