package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface EstateDAO {

    String createEstate(Estate newEstate) throws DietiEstateException;
    String changeEstate(Estate changEstate);
    Agent getAgent(Estate agency);

}
