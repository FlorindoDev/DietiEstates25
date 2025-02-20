package org.dao.Interfacce;



import org.exc.DietiEstateException;
import org.md.Utente.Utente;

public interface UtenteDAO {
    public Utente getUser(Utente utente) throws DietiEstateException;

}
