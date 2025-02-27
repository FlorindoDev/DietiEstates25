package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Geolocalizzazione.Indirizzo;

public interface IndirizzoDAO {
    Indirizzo getIndirizzoFromId(int idindirizzo) throws DietiEstateException;

    boolean isAddressExistsByID(int idindirizzo) throws DietiEstateException;

    boolean isAddressExistsByALL(Indirizzo addr) throws DietiEstateException;

    boolean isAddressNotExistsByALL(Indirizzo addr) throws DietiEstateException;

    boolean createAddress(Indirizzo indirizzo) throws DietiEstateException;

    int getLastAddressId() throws DietiEstateException;
}
