package org.exc.DataBaseException;

public class AgencyAdminsNotExists extends DietiEstateDBexception{
    public AgencyAdminsNotExists() {
        super("{\"code\": 13 , \"message\": \" Agency don't have admins\"}");
    }
}
