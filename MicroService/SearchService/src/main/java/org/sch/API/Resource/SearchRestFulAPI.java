package org.sch.API.Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response allCity() {
        String result = searchService.allCity();
        searchService.close();
        return Response.ok(result).build();
    }


    @Path("suggestionCities")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response suggestionCities(@BeanParam Indirizzo indirizzo) {

        if( indirizzo.getCitta() == null && indirizzo.getQuartiere() == null && indirizzo.getStato() == null && indirizzo.getVia() == null){
            return Response.ok("{\"code\":-1, \"error\": \"A indication parameter is required\"}").build();
        }

        String result = searchService.suggestionCities(indirizzo);
        searchService.close();
        return Response.ok(result).build();
    }

    // todo GET
    @Path("coordinatesEstates")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response coordinatesEstates(Estate estate) {
        return null;

    }


    @Path("estatesSerachFromCity")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response estatesSerachFromCity(@BeanParam Indirizzo indirizzo) {

        if(indirizzo.getCitta() == null){
            return Response.ok("{\"code\":-1, \"error\": \"City parameter is required\"}").build();
        }

        String result = searchService.estatesSerachFromCity(indirizzo);
        searchService.close();
        return Response.ok(result).build();
    }
}
