package org.ma.MainApp.Interface;

import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

public interface ManagementAccountService {

    void applyChangeAcquirente(Acquirente utente);

    void applyChangeAgent(Agent utente);

    void applyChangeAdmin(Admin utente);

    String getAccountAcquirente(Acquirente utente);

    String getAccountAdmin(Admin utente);

    String getAccountAgent(Agent utente);

}
