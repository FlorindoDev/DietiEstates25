package org.exc.DataBaseException;

public class AgencyAgentsNotExists extends DietiEstateDBexception{
    public AgencyAgentsNotExists() {
        super("{\"code\": 4 , \"message\": \" Agency don't have agents\"}");
    }
}
