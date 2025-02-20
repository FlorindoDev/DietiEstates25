package org.exc.DietiEstateMicroServiceException;

public class UserPassowordNotValid extends DietiEstateMicroSercviceException{
    public UserPassowordNotValid() {
        super("{\"code\": 102 , \"message\": \" User Password not valid\"}");
    }
}
