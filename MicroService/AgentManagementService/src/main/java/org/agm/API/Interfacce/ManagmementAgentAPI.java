package org.agm.API.Interfacce;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.core.Response;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

public interface ManagmementAgentAPI {

    Response addAgent(Agent agent);

    Response removeAgent(@BeanParam Agent agent);

    Response getAgents(@BeanParam Agency query);

    Response getEstates(@BeanParam Agency query);

    Response updateEstateAgent(Estate estate);


}
