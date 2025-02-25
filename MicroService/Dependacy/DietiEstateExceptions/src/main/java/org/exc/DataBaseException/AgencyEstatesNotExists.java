package org.exc.DataBaseException;

public class AgencyEstatesNotExists extends DietiEstateDBexception{
    public AgencyEstatesNotExists() {
        super("{\"code\": 4 , \"message\": \" Agency don't have estates\"}");
    }
}
