package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

public interface AcquirenteDAO{
    public Acquirente getUser(Acquirente acquirente) throws DietiEstateException;

    public void createUser(Acquirente acquirente) throws DietiEstateException;

    public void updateUser(Acquirente changes) throws DietiEstateException;

    public boolean isUserAbsent(Acquirente changes) throws DietiEstateException;

    boolean isUserPresent(Acquirente acquirente) throws DietiEstateException;
}
