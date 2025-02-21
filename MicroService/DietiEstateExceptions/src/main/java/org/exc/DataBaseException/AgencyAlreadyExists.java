package org.exc.DataBaseException;

public class AgencyAlreadyExists extends DietiEstateDBexception{
    public AgencyAlreadyExists() {
        super("{\"code\": 4 , \"message\": \" Agency Already Exists\"}");
    }
}
