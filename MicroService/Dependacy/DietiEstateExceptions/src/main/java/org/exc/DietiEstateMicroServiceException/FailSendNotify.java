package org.exc.DietiEstateMicroServiceException;

public class FailSendNotify extends DietiEstateMicroSercviceException{
    public FailSendNotify() {
        super("{\"code\": 109 , \"message\": \" Fail to send notify\"}");
    }

    public FailSendNotify(String message) {
        super("{\"code\": 109 , \"message\": \" Fail to send notify: " + message + "\"}");
    }
}
