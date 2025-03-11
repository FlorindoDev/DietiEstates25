package org.dao.Interfacce;

import org.dao.Interfacce.Factory.QueryParametersNotify;
import org.exc.DietiEstateException;
import org.md.Notify.Notify;
import org.md.Utente.Acquirente;

import java.util.List;

public interface NotifyDAO {
    void createNotify(Notify notify) throws DietiEstateException;

    List<Notify> getNotifyAcquirenteAllFilter(String query, QueryParametersNotify parameters) throws DietiEstateException;

    void close();
}
