package org.exc.DataBaseException;

public class UserNotifyNotFound extends DietiEstateDBexception{
    public UserNotifyNotFound() {
        super("{\"code\": 18 , \"message\": \" User Not Have notify\"}");
    }
}
