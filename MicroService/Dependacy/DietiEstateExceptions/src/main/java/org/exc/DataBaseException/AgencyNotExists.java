package org.exc.DataBaseException;

public class AgencyNotExists extends DietiEstateDBexception{
    public AgencyNotExists() {
        super("{\"code\": 15 , \"message\": \" Agency Not Exists\"}");
    }
}
