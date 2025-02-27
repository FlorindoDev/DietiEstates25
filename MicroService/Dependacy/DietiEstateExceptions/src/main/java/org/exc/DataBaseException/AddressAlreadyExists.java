package org.exc.DataBaseException;

public class AddressAlreadyExists extends DietiEstateDBexception{

    public AddressAlreadyExists() {
            super("{\"code\": 109 , \"message\": \" Address Not Exists\"}");
        }

}
