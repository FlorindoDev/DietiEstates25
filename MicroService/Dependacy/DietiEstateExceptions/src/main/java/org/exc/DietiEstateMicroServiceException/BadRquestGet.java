package org.exc.DietiEstateMicroServiceException;

public class BadRquestGet extends DietiEstateMicroSercviceException{
    public BadRquestGet() {
        super("{\"code\": 111 , \"message\": \" bad request\"}");
    }

    public BadRquestGet(String message) {
        super("{\"code\": 111 , \"message\": \" Bad request: " + message + "\"}");
    }
}
