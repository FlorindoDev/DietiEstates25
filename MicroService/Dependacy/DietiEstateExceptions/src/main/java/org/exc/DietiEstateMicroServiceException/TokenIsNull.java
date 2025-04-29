package org.exc.DietiEstateMicroServiceException;

public class TokenIsNull extends DietiEstateMicroSercviceException{
    public TokenIsNull() {
        super("{\"code\": 114 , \"message\": \" Token is null\"}");
    }
}
