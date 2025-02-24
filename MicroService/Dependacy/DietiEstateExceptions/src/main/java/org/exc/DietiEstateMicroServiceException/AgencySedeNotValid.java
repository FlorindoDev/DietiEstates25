package org.exc.DietiEstateMicroServiceException;

public class AgencySedeNotValid extends DietiEstateMicroSercviceException{
    public AgencySedeNotValid() {
        super("{\"code\": 105 , \"message\": \" Sede of agency not valid\"}");
    }
}
