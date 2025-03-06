package org.ads.MainApp.Interface;

import org.md.Agency.Agency;
import org.md.Estate.Estate;

public interface AdsEstateService {

    String createEstate(Estate estate);
    String changeEstate(Estate estate);
    String loadEstate(Agency estate);

    void close();

}
