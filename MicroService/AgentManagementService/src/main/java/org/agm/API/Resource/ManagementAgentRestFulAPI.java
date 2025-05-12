package org.agm.API.Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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


    @GET
    @Path("getAgents")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getAgents(@BeanParam Agency agency) {

        if(agency.getCodicePartitaIVA().equals("")){
            return Response.ok("{\"code\":-1, \"error\": \"codicePartitaIVA parameter is required\"}").build();
        }

        String result = managementAgent.getAgents(agency);
        managementAgent.close();
        return Response.ok(result).build();
    }


    @GET
    @Path("getEstates")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getEstates(@BeanParam Agency agency) {

        if(agency.getCodicePartitaIVA().equals("")){
            return Response.ok("{\"code\":-1, \"error\": \"codicePartitaIVA parameter is required\"}").build();
        }

        String result = managementAgent.getEstates(agency);
        managementAgent.close();
        return Response.ok(result).build();
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
