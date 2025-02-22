package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

import java.util.ArrayList;

public interface AgencyDAO {
    public void createAgency(Agency agency) throws DietiEstateException;

    public boolean isAgencyAbsent(Agency agency) throws DietiEstateException;

    public boolean isNameAgencyAbsent(Agency agency) throws DietiEstateException;

    public ArrayList<Admin> getAdmins(Agency agency) throws DietiEstateException;

    public ArrayList<Agent> getAgents(org.md.Agency.Agency agency) throws DietiEstateException;

    public ArrayList<Estate> getEstates(Agency agency) throws DietiEstateException;
}
