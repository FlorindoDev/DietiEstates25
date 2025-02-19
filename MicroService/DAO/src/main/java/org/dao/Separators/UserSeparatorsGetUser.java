package org.dao.Separators;

import org.dao.Interfacce.UtenteDAO;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;
import org.md.Utente.interfacce.UserSeparators;

public class UserSeparatorsGetUser implements UserSeparators {

    private UtenteDAO utenteDAO;
    private Utente user = null;

    public Utente getUser() {
        return user;
    }

    public UserSeparatorsGetUser(UtenteDAO utenteDAO) {
        this.utenteDAO = utenteDAO;
    }

    @Override
    public void separator(Utente user) {
        this.user=utenteDAO.getUser(user);
    }

    @Override
    public void separator(Agent agent) {
        user=utenteDAO.getUser(agent);
    }

    @Override
    public void separator(Admin admin) {
        user=utenteDAO.getUser(admin);
    }

    public void separator(Acquirente acquirente) {
        user=utenteDAO.getUser(acquirente);
    }
}
