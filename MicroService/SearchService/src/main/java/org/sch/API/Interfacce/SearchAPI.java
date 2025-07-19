package org.sch.API.Interfacce;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Estate.EstateFilter;
import org.md.Utente.Utente;

public interface SearchAPI {

    public Response allCity();

    public Response suggestionCities(Indirizzo indirizzo );

    public Response coordinatesEstates(Estate estate );

    // todo GET
    @Path("historySearch")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response historySearch(Utente utente);

    public Response estatesSerachFromCity(Indirizzo indirizzo);

    public Response estates(EstateFilter filter, @Context HttpHeaders headers);
}
