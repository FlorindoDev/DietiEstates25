package org.agm.API.Interfacce;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.core.Response;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface ManagmementAgentAPI {

    String addAgent(Agent agent);

    Response removeAgent(@BeanParam Agent agent);

    Response getAgents(@BeanParam Agency query);

    Response getEstates(@BeanParam Agency query);

    String updateEstateAgent(Estate estate);


}
