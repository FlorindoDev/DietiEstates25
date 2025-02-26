package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface EstateDAO {

    void createEstate(Estate newEstate) throws DietiEstateException;
    void changeEstate(Estate changEstate) throws DietiEstateException;
    Agent getAgent(Estate agency);

    void addEstateAgent(Estate estate, Agent agent) throws DietiEstateException;

    boolean isEstatePresent(Estate estate) throws DietiEstateException;
}
