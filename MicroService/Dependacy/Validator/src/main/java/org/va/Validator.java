package org.va;

import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.UserGeneralityNotValid;


public interface Validator {

    Boolean validateEmail(String email) throws DietiEstateException;
    Boolean validatePassword(String password)throws DietiEstateException;
    Boolean validateGenerality(String name,String cognome)throws DietiEstateException;

    boolean validateName(String nome) throws UserGeneralityNotValid;

    boolean validateCognome(String cognome) throws UserGeneralityNotValid;

    boolean validateDate(String date) throws DietiEstateException;

    Boolean validatePartitaIVA(String partiaIVA) throws DietiEstateException;

    Boolean validateSede(String sede) throws DietiEstateException;

    Boolean validateAgencyName(String agencyName) throws DietiEstateException;

}
