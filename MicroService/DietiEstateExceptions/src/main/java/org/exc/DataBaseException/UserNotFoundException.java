package org.exc.DataBaseException;

import org.exc.DataBaseException.DietiEstateDBexception;

public class UserNotFoundException extends DietiEstateDBexception {
    public UserNotFoundException() {
        super("{\"code\": 1 , \"error\": \" Username or password not found\"}");
    }

}
