package org.exc.DataBaseException;

import org.exc.DataBaseException.DietiEstateDBexception;

public class ErrorCreateStatment extends DietiEstateDBexception {
    public ErrorCreateStatment() {
        super("{\"code\": 3 , \"error\": \" Error making statment\"}");
    }


}
