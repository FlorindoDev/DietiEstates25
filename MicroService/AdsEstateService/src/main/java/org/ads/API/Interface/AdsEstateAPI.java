package org.ads.API.Interface;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.md.Agency.Agency;
import org.md.Estate.Estate;

public interface AdsEstateAPI {

    String createEstate(Estate estate);
    String changeEstate(Estate estate);
    String loadEstate(@QueryParam("id") Integer idImmobile);
}
