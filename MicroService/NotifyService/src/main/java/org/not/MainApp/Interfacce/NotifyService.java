package org.not.MainApp.Interfacce;


import org.dao.Interfacce.Factory.QueryParametersNotify;


public interface NotifyService {

    String getNotifyAcquirente(QueryParametersNotify parameters);

    String getNotifyAgent(QueryParametersNotify parameters);

    void close();
}
