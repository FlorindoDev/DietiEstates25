package org.exc;

public class ErrorExecutingQuery extends DietiEstateDBexception{
    public ErrorExecutingQuery(){super("{\"code\": 2 , \"error\": \" Error query execute\"}");}
}
