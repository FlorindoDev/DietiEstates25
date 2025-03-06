package org.agm.MainApp.Interfacce;

import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface ManagmentAgentService {

    String addAgent(Agent agent);

    String removeAgent(Agent agent);

    String getAgents(Agency agency);

    String getEstates(Agency agency);

    String updateEstateAgent(Estate estate, Agent agent);

    void close();

}
