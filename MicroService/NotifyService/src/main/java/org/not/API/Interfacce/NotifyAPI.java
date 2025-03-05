package org.not.API.Interfacce;

import org.md.Utente.Acquirente;
import org.md.Utente.Agent;


public interface NotifyAPI {
    String getNotifyAgent(Agent agent);

    String getNotifyAcquirente(Acquirente acquirente);
}
