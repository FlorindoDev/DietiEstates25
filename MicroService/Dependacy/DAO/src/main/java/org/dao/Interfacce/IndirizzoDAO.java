package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Geolocalizzazione.Indirizzo;

public interface IndirizzoDAO {
    Indirizzo getIndirizzoFromId(int idindirizzo) throws DietiEstateException;
}
