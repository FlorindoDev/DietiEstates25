package org.exc.DietiEstateMicroServiceException;

public class ErrorCreatingAgency extends DietiEstateMicroSercviceException{
    public ErrorCreatingAgency() {
        super("{\"code\": 107 , \"message\": \" Error creating agency\"}");
    }
}
