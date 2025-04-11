package org.not.MainApp.Interfacce;


import org.dao.Interfacce.Factory.QueryParametersNotify;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;


public interface NotifyService {

    String getNotifyAcquirente(QueryParametersNotify parameters);

    String getNotifyAgent(QueryParametersNotify parameters);

    void close();
}
