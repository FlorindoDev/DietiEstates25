package org.sch.MainApp.Interfacce;

import org.md.Geolocalizzazione.Indirizzo;
import org.md.Estate.EstateFilter;

public interface SearchService {
    String allCity();

    String suggestionCities(Indirizzo indirizzo);

    String estatesSerachFromCity(Indirizzo indirizzo);

    void close();

    String search(EstateFilter filter);
}
