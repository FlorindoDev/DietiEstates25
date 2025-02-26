package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

import java.util.List;

public interface AgencyDAO {
    public void createAgency(Agency agency) throws DietiEstateException;

    public boolean isAgencyAbsent(Agency agency) throws DietiEstateException;

    public boolean isNameAgencyAbsent(Agency agency) throws DietiEstateException;

    public List<Admin> getAdmins(Agency agency) throws DietiEstateException;

    public List<Agent> getAgents(org.md.Agency.Agency agency) throws DietiEstateException;

    public List<Estate> getEstates(Agency agency) throws DietiEstateException;

    boolean isAgencyPresent(Agency agency) throws DietiEstateException;
}
