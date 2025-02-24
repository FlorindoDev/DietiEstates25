package org.ap.Validator;

import org.ap.Validator.Interfacce.Validator;
import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.AppointmentDateIsNotValid;

import java.time.LocalDate;

public class Validate implements Validator {
    
    @Override
    public boolean validateDate(String Date) throws DietiEstateException {

        try{
            LocalDate.parse(Date);
            return true;
        }catch (Exception e){
            throw new AppointmentDateIsNotValid();
        }

    }
}
