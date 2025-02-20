package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Agency.Agency;

public interface AgencyDAO {
    public void createAgency(Agency agency) throws DietiEstateException;
}
