package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

public interface AcquirenteDAO{
    Acquirente getUser(Acquirente acquirente) throws DietiEstateException;

    void createUser(Acquirente acquirente) throws DietiEstateException;

    void updateUser(Acquirente changes) throws DietiEstateException;

    boolean isUserAbsent(Acquirente changes) throws DietiEstateException;

    boolean isUserPresent(Acquirente acquirente) throws DietiEstateException;

    boolean isUserAbsentOverAll(Acquirente acquirente) throws DietiEstateException;
}
