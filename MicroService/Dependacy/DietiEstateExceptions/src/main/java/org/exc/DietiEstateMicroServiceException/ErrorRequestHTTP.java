package org.exc.DietiEstateMicroServiceException;

public class ErrorRequestHTTP extends DietiEstateMicroSercviceException{
    public ErrorRequestHTTP() {
        super("{\"code\": 112 , \"message\": \" Error Request HTTP\"}");
    }

    public ErrorRequestHTTP(String message) {
        super("{\"code\": 112 , \"code_status\": \"" + message + "\"}");
    }
}
