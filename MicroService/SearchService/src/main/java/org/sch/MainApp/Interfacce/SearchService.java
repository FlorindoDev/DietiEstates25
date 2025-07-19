package org.sch.MainApp.Interfacce;

import org.exc.DietiEstateException;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Estate.EstateFilter;
import org.md.Utente.Utente;


public interface SearchService {
    String allCity();

    String suggestionCities(Indirizzo indirizzo);

    String estatesSerachFromCity(Indirizzo indirizzo);

    void close();

    String search(EstateFilter filter, String email);

    String historySearch(Utente utente) throws DietiEstateException;
}
