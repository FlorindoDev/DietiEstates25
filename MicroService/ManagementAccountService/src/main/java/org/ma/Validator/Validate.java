package org.ma.Validator;

import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.UserEmailNotValid;
import org.exc.DietiEstateMicroServiceException.UserGeneralityNotValid;
import org.exc.DietiEstateMicroServiceException.UserPassowordNotValid;
import org.ma.Validator.Interface.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate implements Validator {

    private static Validate validate = null;

    private Validate() {} // costruttore privato

    public static Validate getInstance(){
        if(validate == null){
            validate = new Validate();
            return validate;
        }
        return validate;
    }

    // metodi
    @Override
    public Boolean validateEmail(String email) throws DietiEstateException {

        if(email == null) throw new UserEmailNotValid();

        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
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
    public Boolean ValidateName(String firstName, String lastName) throws DietiEstateException {
        if(firstName == null || lastName == null) throw new UserGeneralityNotValid();

        String NAME_REGEX = "^[a-zA-ZÀ-ÖØ-öø-ÿ'’]+$";
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher nome = pattern.matcher(firstName);
        Matcher surname = pattern.matcher(lastName);

        if(!nome.matches() || !surname.matches()){
            throw new UserGeneralityNotValid();
        }
        return true;
    }


}