package org.va.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.md.Estate.Estate;
import org.va.API.Interfacce.ManagmementAgentAPI;
import org.va.MainApp.Interfacce.ManagmentAgentService;
import org.va.MainApp.ManagementAgent;
import org.md.Agency.Agency;
import org.md.Utente.Agent;

@Path("ManagementAgent")
public class ManagementAgentRestFulAPI implements ManagmementAgentAPI {

    ManagmentAgentService managementAgent =  new ManagementAgent();

    @POST
    @Path("addAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String addAgent(Agent agent) {
        return managementAgent.addAgent(agent);
    }

    @POST
    @Path("removeAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String removeAgent(Agent agent) {
        return managementAgent.removeAgent(agent);
    }

    @POST
    @Path("getAgents")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String getAgents(Agency agency) {
        return managementAgent.getAgents(agency);
    }

    @POST
    @Path("getEstates")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String getEstates(Agency agency) {
        return managementAgent.getEstates(agency);
    }

    @POST
    @Path("addEstateAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String addEstateAgent(Estate estate, Agent agent) {
        return managementAgent.addEstateAgent(estate,agent);
    }

    @POST
    @Path("removeEstateAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String removeEstateAgent(Estate estate, Agent agent) {
        return managementAgent.removeEstateAgent(estate,agent);
    }
}
