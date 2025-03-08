package org.dao.Interfacce;



import org.exc.DietiEstateException;
import org.md.Utente.Utente;

public interface UtenteDAO {
    public Utente getUserFromEmail(String email) throws DietiEstateException;

    Utente getUser(Utente utente) throws DietiEstateException;

    void updateUser(Utente changes, String tabella) throws DietiEstateException;

    boolean isUserAbsent(Utente acquirente, String table) throws DietiEstateException;

    boolean isUserPresent(Utente utente, String table) throws DietiEstateException;

    boolean isUserAbsentOverAll(Utente utente) throws DietiEstateException;

    void close();

}
