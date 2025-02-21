package org.ac.Validator.interfacce;

import org.exc.DietiEstateException;

public interface Validator {
    public Boolean validateEmail(String email) throws DietiEstateException;
    public  Boolean validatePassword(String password)throws DietiEstateException;
    public Boolean validateGenerality(String name,String cognome)throws DietiEstateException;
}
