package org.ads.MainApp.Interface;

import org.md.Estate.Estate;

public interface AdsEstateService {

    String createEstate(Estate estate);
    String changeEstate(Estate estate);
    String loadEstate(Integer idImmobile);

    void close();
}
