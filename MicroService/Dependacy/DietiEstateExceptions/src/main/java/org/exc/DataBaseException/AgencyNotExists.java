package org.exc.DataBaseException;

public class AgencyNotExists extends DietiEstateDBexception{
    public AgencyNotExists() {
        super("{\"code\": 4 , \"message\": \" Agency Not Exists\"}");
    }
}
