package org.exc.DataBaseException;

public class AgencyNameAlreadyExists extends DietiEstateDBexception{
    public AgencyNameAlreadyExists() {
        super("{\"code\": 5 , \"message\": \" Agency name already exists\"}");
    }
}
