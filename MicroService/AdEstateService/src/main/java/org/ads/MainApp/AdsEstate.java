package org.ads.MainApp;

import org.ads.MainApp.Interface.AdsEstateService;
import org.dao.Interfacce.EstateDAO;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
// IMPORTARE MQ

public class AdsEstate implements AdsEstateService {

    private EstateDAO ad_estate;

    // RIFERIMENTO A MQ

    @Override
    public String createEsteate(Estate estate) {
        return "";
    }

    @Override
    public String changeEstate(Estate estate) {
        return "";
    }

    @Override
    public String loadEstate(Agency estate) {
        return "";
    }
}
