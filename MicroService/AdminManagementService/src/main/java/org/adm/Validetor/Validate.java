package org.adm.Validetor;

import org.adm.Validetor.Interfacce.Validetor;
import org.exc.DietiEstateMicroServiceException.UserEmailNotValid;
import org.exc.DietiEstateMicroServiceException.UserGeneralityNotValid;
import org.exc.DietiEstateMicroServiceException.UserPassowordNotValid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate implements Validetor {

    private static Validate validate = null;

    private Validate() {}

    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String NAME_REGEX = "^[a-zA-ZÀ-ÖØ-öø-ÿ'’ -]+$";

    public static Validate getInstance(){
        if(validate == null){
            validate = new Validate();
            return validate;
        }
        return validate;
    }

    @Override
    public boolean validateName(String name) throws UserGeneralityNotValid {
        if(name == null ) throw new UserGeneralityNotValid();

        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher nome = pattern.matcher(name);

        if(!nome.matches()){
            throw new UserGeneralityNotValid();
        }
        return true;
    }

    @Override
    public boolean validateCognome(String surname) throws UserGeneralityNotValid {
        if(surname == null ) throw new UserGeneralityNotValid();

        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher cognome = pattern.matcher(surname);

        if(!cognome.matches()){
            throw new UserGeneralityNotValid();
        }
        return true;
    }

    @Override
    public boolean validateEmail(String email) throws UserEmailNotValid {
        if(email == null) throw new UserEmailNotValid();

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new UserEmailNotValid();
        }

        return true;
    }

    @Override
    public boolean validatePassword(String password) throws UserEmailNotValid, UserPassowordNotValid {
        if(password == null) throw new UserEmailNotValid();

        if(password.length() < 8){
            throw new UserPassowordNotValid();
        }
        return true;

    }
}
