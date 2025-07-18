package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Estate.EstateFilter;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Utente.Agent;

import java.util.List;

public interface CronologiaRicercaDAO {


    void add(String queryParams, String email) throws DietiEstateException;
}