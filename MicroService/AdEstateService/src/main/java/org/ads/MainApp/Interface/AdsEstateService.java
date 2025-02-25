package org.ads.MainApp.Interface;

import org.md.Agency.Agency;
import org.md.Estate.Estate;

public interface AdsEstateService {

    String createEsteate(Estate estate);
    String changeEstate(Estate estate);
    String loadEstate(Agency estate);

}
