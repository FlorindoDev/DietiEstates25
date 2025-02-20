package org.exc.DataBaseException;

public class UserElreadyExists extends DietiEstateDBexception{
    public UserElreadyExists() {
        super("{\"code\": 3 , \"error\": \" User Elready Exists\"}");
    }
}
