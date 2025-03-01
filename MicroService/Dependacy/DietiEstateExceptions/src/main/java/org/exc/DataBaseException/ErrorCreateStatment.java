package org.exc.DataBaseException;

public class ErrorCreateStatment extends DietiEstateDBexception {
    public ErrorCreateStatment() {
        super("{\"code\": 17 , \"message\": \" Error making statment\"}");
    }


}
