package org.ap.Validator.Interfacce;

import org.exc.DietiEstateException;

public interface Validator {

    boolean validateDate(String date) throws DietiEstateException;
}
