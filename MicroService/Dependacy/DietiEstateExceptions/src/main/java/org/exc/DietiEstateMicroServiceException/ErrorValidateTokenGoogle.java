package org.exc.DietiEstateMicroServiceException;

public class ErrorValidateTokenGoogle extends DietiEstateMicroSercviceException{

    public ErrorValidateTokenGoogle() {
        super("{\"code\": 113 , \"message\": \" Error Validate google token\"}");
    }

    public ErrorValidateTokenGoogle(String message) {
        super("{\"code\": 113 , \"message\": \" Error Validate google token: " + message + "\"}");
    }
}
