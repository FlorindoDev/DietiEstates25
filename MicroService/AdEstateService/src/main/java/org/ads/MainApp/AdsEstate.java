package org.ads.MainApp;

import org.ads.MainApp.Interface.AdsEstateService;
import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
// IMPORTARE MQ

public class AdsEstate implements AdsEstateService {

    private EstateDAO ad_estate;

    // RIFERIMENTO A MQ

    @Override
    public String createEsteate(Estate estate) {

//        EstateDAO dao = new EstatePostgreDAO();
//        dao.getEstate(null);
        return "";
    }

    @Override
    public String changeEstate(Estate estate) {
        return "";
    }

    @Override
    public String loadEstate(Agency agency) {
        EstateDAO dao = new EstatePostgreDAO();
        try{
            dao.getEstate(agency);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
