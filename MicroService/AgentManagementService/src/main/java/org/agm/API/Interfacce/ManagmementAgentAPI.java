package org.agm.API.Interfacce;

import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface ManagmementAgentAPI {

    String addAgent(Agent agent);

    String removeAgent(Agent agent);

    String getAgents(Agency agency);

    String getEstates(Agency agency);

    String updateEstateAgent(Estate estate);


}
