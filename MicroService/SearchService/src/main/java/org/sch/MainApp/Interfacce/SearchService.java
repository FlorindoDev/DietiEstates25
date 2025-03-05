package org.sch.MainApp.Interfacce;

import org.md.Geolocalizzazione.Indirizzo;

public interface SearchService {
    String allCity();

    String suggestionCities(Indirizzo indirizzo);

    String estatesSerachFromCity(Indirizzo indirizzo);
}
