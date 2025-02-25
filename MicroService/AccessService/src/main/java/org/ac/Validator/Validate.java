package org.ac.Validator;

import org.ac.Validator.interfacce.Validator;
import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.UserEmailNotValid;
import org.exc.DietiEstateMicroServiceException.UserGeneralityNotValid;
import org.exc.DietiEstateMicroServiceException.UserPassowordNotValid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate implements Validator {

    private static Validate validate = null;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String NAME_REGEX = "^[a-zA-ZÀ-ÖØ-öø-ÿ'’ -]+$";
    private Validate() {}

    public static Validate getInstance(){
        if(validate == null){
            validate = new Validate();
            return validate;
        }
        return validate;
    }
    @Override
    public Boolean validateEmail(String email) throws DietiEstateException {

        if(email == null) throw new UserEmailNotValid();

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new UserEmailNotValid();
        }

        return true;
    }

    @Override
    public Boolean validatePassword(String password)throws DietiEstateException {

        if(password == null) throw new UserEmailNotValid();

        if(password.length() < 8){
            throw new UserPassowordNotValid();
        }
        return true;
    }

    @Override
    public Boolean validateGenerality(String name, String cognome)throws DietiEstateException {

        if(name == null || cognome == null) throw new UserGeneralityNotValid();

        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher nome = pattern.matcher(name);
        Matcher surname = pattern.matcher(cognome);

        if(!nome.matches() || !surname.matches()){
            throw new UserGeneralityNotValid();
        }
        return true;
    }
}
