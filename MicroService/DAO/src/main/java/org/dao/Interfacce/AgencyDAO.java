package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Utente.Admin;

import java.util.ArrayList;

public interface AgencyDAO {
    public void createAgency(Agency agency) throws DietiEstateException;

    public boolean isAgencyAbsent(Agency agency) throws DietiEstateException;

    public boolean isNameAgencyAbsent(Agency agency) throws DietiEstateException;

    public ArrayList<Admin> getAdmins(Agency agency) throws DietiEstateException;
}
