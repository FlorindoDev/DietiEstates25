package org.exc;

//{super("{\"code\": 2 , \"error\": \" Error execute query\", \"extraInfo\": \" " + message + "\"}");}

public class DietiEstateException extends Exception{
    public DietiEstateException(String message) {
        super(message);
    }
}
