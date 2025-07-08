package org.ads.API.Interface;

import jakarta.ws.rs.*;
import org.md.Estate.Estate;

public interface AdsEstateAPI {

    String createEstate(Estate estate);
    String changeEstate(Estate estate);
    String loadEstate(@QueryParam("id") Integer idImmobile);
}
