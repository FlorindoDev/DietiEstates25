package org.ag.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.ag.API.Interfacce.CreateAgencyAPI;
import org.ag.MainApp.CreateAgency;
import org.ag.MainApp.Interfacce.CreateAgencyService;
import org.md.Agency.Agency;
@Path("create")
public class CreateAgencyRestFulAPI implements CreateAgencyAPI {

    private final CreateAgencyService createAgnecy = new CreateAgency();
    @Override
    @POST
    @Path("createAgency")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createAgency(Agency agency) {
        String result = createAgnecy.makeAgency(agency);
        createAgnecy.close();
        return result;
    }
}
