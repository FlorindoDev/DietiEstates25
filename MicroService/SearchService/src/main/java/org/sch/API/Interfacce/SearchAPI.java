package org.sch.API.Interfacce;

import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;

public interface SearchAPI {

    public String allCity();

    public String suggestionCities(Indirizzo indirizzo );

    public String coordinatesEstates(Estate estate );

    public String estatesSerachFromCity(Indirizzo indirizzo);


}
