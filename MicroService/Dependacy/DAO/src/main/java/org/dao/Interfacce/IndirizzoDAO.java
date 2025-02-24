package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Estate.Indirizzo;

public interface IndirizzoDAO {
    Indirizzo getIndirizzoFromId(String idindirizzo) throws DietiEstateException;
}
