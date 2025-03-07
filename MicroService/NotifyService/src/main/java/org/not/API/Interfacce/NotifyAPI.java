package org.not.API.Interfacce;

import jakarta.ws.rs.QueryParam;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;


public interface NotifyAPI {
    String getNotifyAgent(Agent agent);

    String getNotifyAcquirente(@QueryParam("email") String email, @QueryParam("orderbydate") boolean order);

}
