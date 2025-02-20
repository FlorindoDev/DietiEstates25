package org.ac.Validaitor.interfacce;

import org.exc.DietiEstateException;

public interface Validaitor {
    public Boolean validateEmail(String email) throws DietiEstateException;
    public  Boolean validatePassword(String password)throws DietiEstateException;
    public Boolean validateGenerality(String name,String cognome)throws DietiEstateException;
}
