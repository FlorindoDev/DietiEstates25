package org.exc.DataBaseException;

public class AddressAlreadyExists extends DietiEstateDBexception{

    public AddressAlreadyExists() {
            super("{\"code\": 12 , \"message\": \" Address Already Exists\"}");
        }

}
