package org.exc;

public class ErrorCreateStatment extends DietiEstateDBexception{
    public ErrorCreateStatment() {
        super("{\"code\": 3 , \"error\": \" Error making statment\"}");
    }
}
