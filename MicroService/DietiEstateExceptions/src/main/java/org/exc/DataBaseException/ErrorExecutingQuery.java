package org.exc.DataBaseException;

import org.exc.DataBaseException.DietiEstateDBexception;

public class ErrorExecutingQuery extends DietiEstateDBexception {
    public ErrorExecutingQuery(){super("{\"code\": 2 , \"message\": \" Error execute query\"}");}


}
