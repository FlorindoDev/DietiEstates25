package org.ag.Validaitor;

import org.ag.Validaitor.Interfacce.Validaitor;
import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.AgencyNameNotValid;
import org.exc.DietiEstateMicroServiceException.UserEmailNotValid;
import org.exc.DietiEstateMicroServiceException.UserGeneralityNotValid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate implements Validaitor {

    private static Validate validate = null;
    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String NAME_REGEX = "^[a-zA-ZÀ-ÖØ-öø-ÿ'’]+$";
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
    public Boolean validatePartitaIVA(String partiaIVA) throws DietiEstateException{
        return false;
    }

    @Override
    public Boolean validateSede(String sede) throws DietiEstateException{
        return false;
    }

    @Override
    public Boolean validateAgencyName(String agencyName) throws DietiEstateException{
        if(agencyName == null )throw new AgencyNameNotValid();

        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher nome = pattern.matcher(agencyName);

        if(!nome.matches()){
            throw new AgencyNameNotValid();
        }
        return true;
    }
}
