package DBLib.DAO.Interfacce;

import org.md.Utente.Utente;

public interface UtenteDAO {
    public Utente getUser(Utente utente);
    public void createUser(Utente user);
    public void updateUser(Utente changes);

}
