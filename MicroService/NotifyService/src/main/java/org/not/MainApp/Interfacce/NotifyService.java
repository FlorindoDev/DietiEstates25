package org.not.MainApp.Interfacce;


import org.md.Utente.Acquirente;
import org.md.Utente.Agent;


public interface NotifyService {

    String getNotifyAcquirente(Acquirente acquirente, String order);

    String getNotifyAgent(Agent agent);

    void close();
}
