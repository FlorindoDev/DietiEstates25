package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Notify.Notify;
import org.md.Utente.Acquirente;

import java.util.ArrayList;

public interface NotifyDAO {
    void createNotify(Notify notify) throws DietiEstateException;

    ArrayList<Notify> getAllNotifyAcquirente(Acquirente acquirente, String order) throws DietiEstateException;

    void close();
}
