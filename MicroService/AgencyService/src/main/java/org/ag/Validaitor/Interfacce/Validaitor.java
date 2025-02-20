package org.ag.Validaitor.Interfacce;

import org.exc.DietiEstateException;

public interface Validaitor {
   public Boolean validateAgencyName(String agencyName) throws DietiEstateException;
   public Boolean validatePartitaIVA(String partiaIVA) throws DietiEstateException;
    public Boolean validateSede(String sede) throws DietiEstateException;
    public Boolean validateEmail(String email) throws DietiEstateException;
}
