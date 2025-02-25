package org.exc.DataBaseException;


public class UserNotFoundException extends DietiEstateDBexception {
    public UserNotFoundException() {
        super("{\"code\": 1 , \"message\": \" Username or password not found\"}");
    }

}
