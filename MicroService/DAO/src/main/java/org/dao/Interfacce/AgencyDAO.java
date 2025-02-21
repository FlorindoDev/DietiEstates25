package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Utente.Acquirente;

public interface AgencyDAO {
    public void createAgency(Agency agency) throws DietiEstateException;

    public boolean isAgencyAbsent(Agency agency) throws DietiEstateException;

    public boolean isNameAgencyAbsent(Agency agency) throws DietiEstateException;
}
