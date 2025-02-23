package org.exc.DataBaseException;

public class UserAlreadyExists extends DietiEstateDBexception{
    public UserAlreadyExists() {
        super("{\"code\": 3 , \"message\": \" User Already Exists\"}");
    }
}
