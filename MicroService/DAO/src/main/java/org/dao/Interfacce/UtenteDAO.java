package org.dao.Interfacce;



import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

public interface UtenteDAO {
    public Utente getUser(Utente utente);
    public Admin getUser(Admin admin);
    public Agent getUser(Agent agent);
    public void createUser(Utente user);
    public void createUser(Admin admin);
    public void createUser(Agent agent);
    public void updateUser(Utente changes);
    public void updateUser(Admin changes);
    public void updateUser(Agent changes);

}
