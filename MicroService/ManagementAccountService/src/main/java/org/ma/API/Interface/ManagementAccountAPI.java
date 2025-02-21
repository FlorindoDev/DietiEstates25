package org.ma.API.Interface;

import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

public interface ManagementAccountAPI {

    void applyChangeAcquirente(Acquirente utente);

    void applyChangeAgent(Agent utente);

    void applyChangeAdmin(Admin utente);

    String getAccountAcquirente(Acquirente utente);

    String getAccountAdmin(Admin utente);

    String getAccountAgent(Agent utente);
}