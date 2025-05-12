package org.agm.API.Interfacce;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.core.Response;
import org.agm.API.Resource.AgentQuery;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface ManagmementAgentAPI {

    String addAgent(Agent agent);

    String removeAgent(Agent agent);

    Response getAgents(@BeanParam AgentQuery query);

    String getEstates(Agency agency);

    String updateEstateAgent(Estate estate);


}
