package org.md.Utente.interfacce;

import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

public interface UserSeparators {
    public void separator (Utente user);
    public void separator(Agent agent);
    public void separator(Admin admin);
    public void separator(Acquirente acquirente);
}
