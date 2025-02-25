package org.dao.Interfacce;

import org.md.Agency.Agency;
import org.md.Estate.Estate;

import java.util.ArrayList;

public interface EstateDAO {

    String createEstate(Estate newEstate);
    String changeEstate(Estate changEstate);
    ArrayList<Estate> getEstate(Agency agency);

}
