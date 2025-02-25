package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

import java.util.ArrayList;

public interface EstateDAO {

    String createEstate(Estate newEstate);
    String changeEstate(Estate changEstate);
    Agent getAgent(Estate agency);

}
