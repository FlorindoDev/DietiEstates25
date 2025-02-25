package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.EstateDAO;
import org.md.Agency.Agency;
import org.md.Estate.Estate;

import java.util.ArrayList;

public class EstatePostgreDAO implements EstateDAO {

    CommunicationWithPostgre connection;

    @Override
    public String createEstate(Estate newEstate) {
        return "";
    }

    @Override
    public String changeEstate(Estate changEstate) {
        return "";
    }

    @Override
    public ArrayList<Estate> getEstate(Agency agency) {
        return null;
    }
}
