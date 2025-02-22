package org.agm.MainApp.Interfacce;

import org.md.Agency.Agency;
import org.md.Utente.Agent;

public interface ManagmentAgentService {

    public String addAgent(Agent agent);

    public String removeAgent(Agent agent);

    public String getAgents(Agency agency);

    public String getEstates(Agency agency);

}
