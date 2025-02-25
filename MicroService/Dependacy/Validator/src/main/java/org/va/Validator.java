package org.va;

import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.UserGeneralityNotValid;


public interface Validator {

    public Boolean validateEmail(String email) throws DietiEstateException;
    public  Boolean validatePassword(String password)throws DietiEstateException;
    public Boolean validateGenerality(String name,String cognome)throws DietiEstateException;

    public boolean validateName(String nome) throws UserGeneralityNotValid;

    public boolean validateCognome(String cognome) throws UserGeneralityNotValid;

    public boolean validateDate(String date) throws DietiEstateException;



}
