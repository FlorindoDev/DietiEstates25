package org.exc.DataBaseException;

public class AddressNotExists extends DietiEstateDBexception{
    public AddressNotExists() {
        super("{\"code\": 4 , \"message\": \" Address Not Exists\"}");
    }
}
