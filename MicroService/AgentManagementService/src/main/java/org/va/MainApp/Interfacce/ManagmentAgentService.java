package org.va.MainApp.Interfacce;

import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface ManagmentAgentService {

    String addAgent(Agent agent);

    String removeAgent(Agent agent);

    String getAgents(Agency agency);

    String getEstates(Agency agency);

    String addEstateAgent(Estate estate, Agent agent);

    String removeEstateAgent(Estate estate, Agent agent);
}
