package org.exc.DietiEstateMicroServiceException;


public class AgencyPartitaIVANotValid extends DietiEstateMicroSercviceException {
    public AgencyPartitaIVANotValid() {
        super("{\"code\": 104 , \"message\": \" PartitaIVA of agency not valid\"}");
    }

    public AgencyPartitaIVANotValid(String message) {
        super("{\"code\": 104 , \"message\": \" PartitaIVA of agency not valid: " + message +"\"}");
    }
}
