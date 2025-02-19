package org.dao.Interfacce;



import org.exc.DietiEstateException;
import org.exc.UserNotFoundException;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

public interface UtenteDAO {
    public Utente getUser(Utente utente) throws DietiEstateException;

}
