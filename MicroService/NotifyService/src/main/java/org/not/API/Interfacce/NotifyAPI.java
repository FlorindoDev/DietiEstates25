package org.not.API.Interfacce;

import jakarta.ws.rs.BeanParam;
import org.md.Utente.Agent;
import org.not.API.Resource.NotifyQuery;


public interface NotifyAPI {
    String getNotifyAgent(Agent agent);

    String getNotifyAcquirente(@BeanParam NotifyQuery query);

}
