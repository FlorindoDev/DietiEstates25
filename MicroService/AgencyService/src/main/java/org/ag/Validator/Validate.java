package org.ag.Validator;

import org.ag.Validator.Interfacce.Validator;
import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate implements Validator {

    private static Validate validate = null;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String NAME_REGEX = "^[a-zA-ZÀ-ÖØ-öø-ÿ'’ ]+$";
    private static final String PARTITAIVA_REGEX = "^[0-9]+$";
    private static final String SEDE_REGEX = "^[a-zA-ZÀ-ÖØ-öø-ÿ0-9'’ ]+$";

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

        if(partiaIVA == null) throw new AgencyPartitaIVANotValid("is null");

        if(partiaIVA.length() != 11 ) throw new AgencyPartitaIVANotValid("is to short or is to long");

        Pattern pattern = Pattern.compile(PARTITAIVA_REGEX);
        Matcher matcher = pattern.matcher(partiaIVA);

        if(!matcher.matches()){
            throw new AgencyPartitaIVANotValid();
        }

        return true;
    }

    @Override
    public Boolean validateSede(String sede) throws DietiEstateException{

        if(sede == null) throw new AgencySedeNotValid();

        Pattern pattern = Pattern.compile(SEDE_REGEX);
        Matcher matcher = pattern.matcher(sede);

        if(!matcher.matches()){
            throw new AgencySedeNotValid();
        }

        return true;
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
