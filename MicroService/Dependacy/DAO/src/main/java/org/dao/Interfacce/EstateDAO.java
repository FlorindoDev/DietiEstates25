package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Utente.Agent;

import java.util.List;

public interface EstateDAO {

    void createEstate(Estate newEstate) throws DietiEstateException;
    void updateEstate(Estate changEstate) throws DietiEstateException;
    Agent getAgent(Estate agency) throws DietiEstateException;
    void updateEstateAgent(Estate estate, Agent agent) throws DietiEstateException;

    boolean isEstatePresent(Estate estate) throws DietiEstateException;

    List<Estate> estatesSerachFromCity(Indirizzo indirizzo) throws DietiEstateException;
    void close();
}
