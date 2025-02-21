package org.exc.DataBaseException;

public class UserNotExists extends DietiEstateDBexception{
    public UserNotExists() {
        super("{\"code\": 6 , \"message\": \" User Not Exists\"}");
    }
}
