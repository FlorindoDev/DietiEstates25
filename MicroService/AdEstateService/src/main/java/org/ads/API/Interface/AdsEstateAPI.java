package org.ads.API.Interface;

import org.md.Agency.Agency;
import org.md.Estate.Estate;

public interface AdsEstateAPI {

    String createEsteate(Estate estate);
    String changeEstate(Estate estate);
    String loadEstate(Agency estate); //ritorna estate collegati ad un agenzia

}
