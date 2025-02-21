package org.ma.Validator.Interface;
import org.exc.DietiEstateException;

public interface Validator {

    public Boolean validateEmail(String email) throws DietiEstateException;
    public Boolean validatePassword(String password) throws DietiEstateException;
    public Boolean ValidateName(String firstName, String lastName) throws DietiEstateException;

}
