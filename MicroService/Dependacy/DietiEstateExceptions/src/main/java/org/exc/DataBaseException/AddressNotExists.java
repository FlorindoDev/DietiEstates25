package org.exc.DataBaseException;

public class AddressNotExists extends DietiEstateDBexception{
    public AddressNotExists() {
        super("{\"code\": 11 , \"message\": \" Address Not Exists\"}");
    }
}
