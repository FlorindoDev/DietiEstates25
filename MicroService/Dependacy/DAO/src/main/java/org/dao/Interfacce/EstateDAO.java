package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface EstateDAO {

    void createEstate(Estate newEstate) throws DietiEstateException;
    String changeEstate(Estate changEstate);
    Agent getAgent(Estate agency);

    void addEstateAgent(Estate estate, Agent agent) throws DietiEstateException;

    boolean IsEstatePresent(Estate estate) throws DietiEstateException;
}
