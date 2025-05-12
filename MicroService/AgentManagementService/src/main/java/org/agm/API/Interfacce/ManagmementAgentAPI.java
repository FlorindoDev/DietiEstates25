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

    Response getEstates(@BeanParam AgentQuery query);

    String updateEstateAgent(Estate estate);


}
