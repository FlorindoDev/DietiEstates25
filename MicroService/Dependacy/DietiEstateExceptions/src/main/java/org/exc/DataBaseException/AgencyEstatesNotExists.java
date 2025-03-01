package org.exc.DataBaseException;

public class AgencyEstatesNotExists extends DietiEstateDBexception{
    public AgencyEstatesNotExists() {
        super("{\"code\": 16 , \"message\": \" Agency don't have estates\"}");
    }
}
