package org.exc.DataBaseException;

public class AddressNotExists extends DietiEstateDBexception{
    public AddressNotExists() {
        super("{\"code\": 108 , \"message\": \" Address Not Exists\"}");
    }
}
