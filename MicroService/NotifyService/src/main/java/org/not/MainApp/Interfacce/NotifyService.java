package org.not.MainApp.Interfacce;


import org.md.Utente.Acquirente;
import org.md.Utente.Agent;


public interface NotifyService {

    String getNotifyAcquirente(Acquirente acquirente);

    String getNotifyAgent(Agent agent);
}
