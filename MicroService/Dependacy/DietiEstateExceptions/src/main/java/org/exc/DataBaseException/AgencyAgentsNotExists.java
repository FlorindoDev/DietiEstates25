package org.exc.DataBaseException;

public class AgencyAgentsNotExists extends DietiEstateDBexception{
    public AgencyAgentsNotExists() {
        super("{\"code\": 14 , \"message\": \" Agency don't have agents\"}");
    }
}
