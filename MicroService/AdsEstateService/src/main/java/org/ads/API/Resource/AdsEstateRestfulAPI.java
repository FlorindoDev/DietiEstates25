package org.ads.API.Resource;

import org.ads.API.Interface.AdsEstateAPI;
import org.ads.MainApp.AdsEstate;
import org.ads.MainApp.Main;
import org.md.Agency.Agency;
import org.md.Estate.Estate;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
    @POST
    @Path("loadEstate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String loadEstate(Agency agency) {
        AdsEstate ads = new AdsEstate(Main.rabbitMQ);
        String response = ads.loadEstate(agency);
        ads.close();
        return response;
    }
}