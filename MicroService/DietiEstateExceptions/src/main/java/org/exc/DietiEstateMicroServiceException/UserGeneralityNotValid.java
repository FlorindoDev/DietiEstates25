package org.exc.DietiEstateMicroServiceException;

public class UserGeneralityNotValid extends DietiEstateMicroSercviceException{
    public UserGeneralityNotValid() {
        super("{\"code\": 103 , \"error\": \" User Generality not valid\"}");
    }
}
