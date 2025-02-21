package org.ma.API.Interface;

import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

public interface ManagementAccountAPI {

    String applyChangeAcquirente(Acquirente utente);

    String applyChangeAgent(Agent utente);

    String applyChangeAdmin(Admin utente);

    String getAccountAcquirente(Acquirente utente);

    String getAccountAdmin(Admin utente);

    String getAccountAgent(Agent utente);
}