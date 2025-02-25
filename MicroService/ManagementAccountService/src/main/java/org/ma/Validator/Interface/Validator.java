package org.ma.Validator.Interface;
import org.exc.DietiEstateException;

public interface Validator {

    public Boolean validateEmail(String email) throws DietiEstateException;
    public Boolean validatePassword(String password) throws DietiEstateException;
    public Boolean validateName(String name) throws DietiEstateException;

}
