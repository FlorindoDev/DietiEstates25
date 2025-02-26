package sch.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import sch.API.Interfacce.SearchAPI;

public class SearchRestFulAPI implements SearchAPI {

    @Path("allCity")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String allCity() {
        return "";
    }

    @Path("suggestionCities")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String suggestionCities(Indirizzo indirizzo) {
        return "";
    }

    @Path("coordinatesEstates")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String coordinatesEstates(Estate estate) {
        return "";
    }
}
