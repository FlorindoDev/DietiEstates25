package org.sch.API.Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import org.sch.API.Interfacce.SearchAPI;
import org.sch.MainApp.Interfacce.SearchService;
import org.sch.MainApp.Search;

@Path("Search")
public class SearchRestFulAPI implements SearchAPI {

    private final SearchService searchService =  new Search();

    @Path("allCity")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String allCity() {
        return searchService.allCity();
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
