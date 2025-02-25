package org.exc.DataBaseException;

public class EstateNotExists extends DietiEstateDBexception{
    public EstateNotExists() {
        super("{\"code\": 4 , \"message\": \" estate Not Exists\"}");
    }
}
