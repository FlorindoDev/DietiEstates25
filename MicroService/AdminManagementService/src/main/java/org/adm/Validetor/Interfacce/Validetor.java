package org.adm.Validetor.Interfacce;

import org.exc.DietiEstateMicroServiceException.UserEmailNotValid;
import org.exc.DietiEstateMicroServiceException.UserGeneralityNotValid;
import org.exc.DietiEstateMicroServiceException.UserPassowordNotValid;

public interface Validetor {


    boolean validateName(String nome) throws UserGeneralityNotValid;

    boolean validateCognome(String cognome) throws UserGeneralityNotValid;

    boolean validateEmail(String email) throws UserEmailNotValid;

    boolean validatePassword(String password) throws UserEmailNotValid, UserPassowordNotValid;
}
