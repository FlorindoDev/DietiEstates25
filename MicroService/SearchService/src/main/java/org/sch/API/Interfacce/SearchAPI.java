package org.sch.API.Interfacce;

import jakarta.ws.rs.core.Response;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;

public interface SearchAPI {

    public String allCity();

    public Response suggestionCities(Indirizzo indirizzo );

    public String coordinatesEstates(Estate estate );

    public Response estatesSerachFromCity(Indirizzo indirizzo);


}
