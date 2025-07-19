package org.sch.API.Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.exc.DietiEstateException;
import org.json.JSONObject;
import org.md.Estate.EstateFilter;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Utente.Utente;
import org.sch.API.Interfacce.SearchAPI;
import org.sch.MainApp.Interfacce.SearchService;
import org.sch.MainApp.Search;
import java.util.Base64;


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

    @Path("historySearch")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response historySearch(@BeanParam Utente utente) {
        if(utente.getEmail() == null) return Response.status(Response.Status.BAD_REQUEST).build();
        String ricerche;
        try {
            ricerche = searchService.historySearch(utente);
        }catch (DietiEstateException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        searchService.close();
        return Response.ok(ricerche).build();

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


    @Path("estates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response estates(@BeanParam EstateFilter filter, @Context HttpHeaders headers) {
        String email = takeEmail(headers);
        if(email == null) return Response.status(Response.Status.UNAUTHORIZED).build();


        String result = searchService.search(filter,email);
        searchService.close();
        return Response.ok(result).build();
    }

    private String takeEmail(HttpHeaders headers) {

        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                // Decodifica il payload (seconda parte del JWT)
                String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
                JSONObject payload = new JSONObject(payloadJson);

                // Estrai il campo "sub" (email)
                email = payload.optString("sub", null);

                // Puoi anche usarla nella logica dell'app
            }
        }
        return email;
    }


}
