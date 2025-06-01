package org.sch.API.Interfacce;

import jakarta.ws.rs.core.Response;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Estate.EstateFilter;

public interface SearchAPI {

    public Response allCity();

    public Response suggestionCities(Indirizzo indirizzo );

    public Response coordinatesEstates(Estate estate );

    public Response estatesSerachFromCity(Indirizzo indirizzo);

    public Response estates(EstateFilter filter);
}
