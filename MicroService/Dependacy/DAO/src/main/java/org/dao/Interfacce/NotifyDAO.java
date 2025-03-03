package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Notify.Notify;

public interface NotifyDAO {
    void createNotify(Notify notify) throws DietiEstateException;
}
