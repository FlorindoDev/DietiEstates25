package org.exc.DietiEstateMicroServiceException;

public class UserEmailNotValid extends DietiEstateMicroSercviceException{
    public UserEmailNotValid() {
        super("{\"code\": 101 , \"message\": \" User email not valid\"}");
    }
}
