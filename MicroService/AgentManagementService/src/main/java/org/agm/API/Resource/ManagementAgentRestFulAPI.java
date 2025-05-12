package org.agm.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.md.Estate.Estate;
import org.agm.API.Interfacce.ManagmementAgentAPI;
import org.agm.MainApp.Interfacce.ManagmentAgentService;
import org.agm.MainApp.ManagementAgent;
import org.md.Agency.Agency;
import org.md.Utente.Agent;

@Path("ManagementAgent")
public class ManagementAgentRestFulAPI implements ManagmementAgentAPI {

    ManagmentAgentService managementAgent =  new ManagementAgent();

    @POST
    @Path("addAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String addAgent(Agent agent) {
        String result = managementAgent.addAgent(agent);
        managementAgent.close();
        return result;
    }

    @POST
    @Path("removeAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String removeAgent(Agent agent) {
        String result = managementAgent.removeAgent(agent);
        managementAgent.close();
        return result;
    }

    //todo GET
    @POST
    @Path("getAgents")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getAgents(Agency agency) {
        String result = managementAgent.getAgents(agency);
        managementAgent.close();
        return result;
    }

    //todo GET
    @POST
    @Path("getEstates")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String getEstates(Agency agency) {
        String result = managementAgent.getEstates(agency);
        managementAgent.close();
        return result;
    }

    @POST
    @Path("UpdateEstateAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String updateEstateAgent(Estate estate) {
        String result = managementAgent.updateEstateAgent(estate,estate.getAgente());
        managementAgent.close();
        return result;
    }

}
