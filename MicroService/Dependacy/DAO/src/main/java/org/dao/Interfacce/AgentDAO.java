package org.dao.Interfacce;

import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DietiEstateException;

import org.md.Utente.Agent;

import java.util.List;

public interface AgentDAO{
    Agent getUser(Agent agent) throws DietiEstateException;

    Agent estraiIdFromEmailAgent(Agent agent) throws ErrorCreateStatment;

    void createUser(Agent changes) throws DietiEstateException;
    void updateUser(Agent changes) throws DietiEstateException;
    boolean isUserAbsent(Agent acquirente) throws DietiEstateException;
    boolean isUserPresent(Agent acquirente) throws DietiEstateException;
    boolean isUserAbsentOverAll(Agent user) throws DietiEstateException;
    void removeAgent(Agent agent) throws DietiEstateException;
    Agent getAgentFromId(Agent agent) throws DietiEstateException;
    List<Integer> getIdEstate(Agent agent) throws DietiEstateException;
    void close();
}
