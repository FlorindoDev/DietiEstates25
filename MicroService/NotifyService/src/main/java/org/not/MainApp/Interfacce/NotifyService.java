package org.not.MainApp.Interfacce;

import org.exc.DietiEstateException;

public interface NotifyService {

    void updateIdNotifyPush(String token) throws DietiEstateException;
}
