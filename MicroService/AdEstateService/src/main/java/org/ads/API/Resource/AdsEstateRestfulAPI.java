package org.ads.API.Resource;

import org.ads.API.Interface.AdsEstateAPI;
import org.ads.MainApp.AdsEstate;
import org.md.Estate.Estate;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("AdsEstate")
public class AdsEstateRestfulAPI implements AdsEstateAPI {

    AdsEstate ads = new AdsEstate();

    @Override
    @POST
    @Path("createEstate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createEsteate(Estate estate) {
        return "";
    }

    @Override
    @POST
    @Path("changeEstate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String changeEstate(Estate estate) {
        return "";
    }

    @Override
    @POST
    @Path("loadEstate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String loadEstate(Estate estate) {
        return "";
    }
}