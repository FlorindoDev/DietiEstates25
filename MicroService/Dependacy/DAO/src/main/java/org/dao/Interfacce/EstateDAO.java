package org.dao.Interfacce;

import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface EstateDAO {

    String createEstate(Estate newEstate);
    String changeEstate(Estate changEstate);
    Agent getAgent(Estate agency);

}
