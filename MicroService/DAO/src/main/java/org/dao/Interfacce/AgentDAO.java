package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

public interface AgentDAO{
    public Agent getUser(Agent agent) throws DietiEstateException;
    public void createUser(Agent changes) throws DietiEstateException;
    public void updateUser(Agent changes) throws DietiEstateException;
}
