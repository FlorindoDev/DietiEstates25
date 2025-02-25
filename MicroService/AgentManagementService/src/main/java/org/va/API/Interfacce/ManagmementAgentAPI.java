package org.va.API.Interfacce;

import org.md.Agency.Agency;
import org.md.Utente.Agent;

public interface ManagmementAgentAPI {

    public String addAgent(Agent agent);

    public String removeAgent(Agent agent);

    public String getAgents(Agency agency);

    public String getEstates(Agency agency);


}
