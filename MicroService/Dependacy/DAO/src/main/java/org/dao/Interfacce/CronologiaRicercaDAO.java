package org.dao.Interfacce;

import org.exc.DietiEstateException;


public interface CronologiaRicercaDAO {


    void close();

    void add(String queryParams, String email) throws DietiEstateException;
}