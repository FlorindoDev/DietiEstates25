package org.ag.Validator.Interfacce;

import org.exc.DietiEstateException;

public interface Validator {
   public Boolean validateAgencyName(String agencyName) throws DietiEstateException;
   public Boolean validatePartitaIVA(String partiaIVA) throws DietiEstateException;
    public Boolean validateSede(String sede) throws DietiEstateException;
    public Boolean validateEmail(String email) throws DietiEstateException;
}
