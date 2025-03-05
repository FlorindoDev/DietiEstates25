package org.exc.DietiEstateMicroServiceException;

public class FailToInsertOnQueue extends DietiEstateMicroSercviceException{
    public FailToInsertOnQueue() {
        super("{\"code\": 108 , \"message\": \" Error to insert into Queue\"}");
    }

    public FailToInsertOnQueue(String message) {
        super("{\"code\": 108 , \"message\": \" Error to insert into Queue: " +  message + "\"}");
    }
}
