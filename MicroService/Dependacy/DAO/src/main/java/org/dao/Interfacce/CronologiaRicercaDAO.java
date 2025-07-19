package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Estate.Ricerca;
import org.md.Utente.Utente;

import java.util.List;


public interface CronologiaRicercaDAO {


    void close();

    void add(String queryParams, String email) throws DietiEstateException;

    List<Ricerca> get(Utente utente) throws DietiEstateException;
}