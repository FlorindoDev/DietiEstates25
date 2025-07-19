package org.ads.API.Resource;

import jakarta.ws.rs.*;
import org.ads.API.Interface.AdsEstateAPI;
import org.ads.MainApp.AdsEstate;
import org.ads.MainApp.Main;
import org.md.Estate.Estate;

import jakarta.ws.rs.core.MediaType;

@Path("AdsEstate")
public class AdsEstateRestfulAPI implements AdsEstateAPI {


    @Override
    @POST
    @Path("createEstate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createEstate(Estate estate) {
        AdsEstate ads = new AdsEstate(Main.rabbitMQ);
        String response = ads.createEstate(estate);
        ads.close();
        return response;
    }

    @Override
    @POST
    @Path("changeEstate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String changeEstate(Estate estate) {
        AdsEstate ads = new AdsEstate(Main.rabbitMQ);
        String response = ads.changeEstate(estate);
        ads.close();
        return response;
    }

    @Override
    @GET
    @Path("getEstate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String loadEstate(@QueryParam("id") Integer idImmobile) {

        AdsEstate ads = new AdsEstate(Main.rabbitMQ);
        String response;
        if (idImmobile == null) {
            response = ads.loadEstate(null);
        } else {
            response = ads.loadEstate(idImmobile);
        }
        ads.close();
        return response;
    }
}