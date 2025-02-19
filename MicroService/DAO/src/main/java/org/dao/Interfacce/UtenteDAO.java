package org.dao.Interfacce;



import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

public interface UtenteDAO {
    public Utente getUser(Utente utente);
    public Admin getUser(Admin admin);
    public Agent getUser(Agent agent);
    public Acquirente getUser(Acquirente acquirente);
    public void createUser(Acquirente acquirente);
    public void createUser(Admin admin);
    public void createUser(Agent agent);
    public void updateUser(Acquirente changes);
    public void updateUser(Admin changes);
    public void updateUser(Agent changes);

}
