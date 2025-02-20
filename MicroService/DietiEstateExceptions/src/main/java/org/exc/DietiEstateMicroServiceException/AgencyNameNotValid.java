package org.exc.DietiEstateMicroServiceException;

import org.exc.DietiEstateException;

public class AgencyNameNotValid extends DietiEstateException {
    public AgencyNameNotValid() {
        super("{\"code\": 103 , \"error\": \" Name of agency not valid\"}");
    }
}
