package org.exc.DataBaseException;

public class EstateNotExists extends DietiEstateDBexception{
    public EstateNotExists() {
        super("{\"code\": 10 , \"message\": \" estate Not Exists\"}");
    }
}
