package org.not.API.Interfacce;

import jakarta.ws.rs.BeanParam;
import org.not.API.Resource.NotifyQuery;


public interface NotifyAPI {
    String getNotifyAgent(@BeanParam NotifyQuery query);

    String getNotifyAcquirente(@BeanParam NotifyQuery query);

}
