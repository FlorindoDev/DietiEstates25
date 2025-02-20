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
@Path("createAgency")
public class CreateAgencyRestFulAPI implements CreateAgencyAPI {

    private CreateAgencyService create_agnecy = new CreateAgency();
    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createAgency(Agency agency) {
        return create_agnecy.makeAgency(agency);
    }
}
