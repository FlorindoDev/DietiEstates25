package org.adm.API.Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.adm.API.Interfacce.ManagementAdminAPI;
import org.adm.MainApp.Interfacce.ManagmentAdminService;
import org.adm.MainApp.ManagementAdmin;
import org.md.Agency.Agency;
import org.md.Utente.Admin;


@Path("ManagementAdmin")
public class ManagementAdminRestFulAPI implements ManagementAdminAPI {

    ManagmentAdminService managementAdmin =  new ManagementAdmin();

    @POST
    @Path("addAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String addAdmin(Admin admin) {
        String result = managementAdmin.addAdmin(admin,admin.getAgency());
        managementAdmin.close();
        return result;
    }

    @DELETE
    @Path("removeAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response removeAdmin(@BeanParam Admin admin) {

        if(admin.getEmail().equals("")){
            return Response.ok("{\"code\":-1, \"error\": \"email parameter is required\"}").build();

        }

        String result = managementAdmin.removeAdmin(admin);
        managementAdmin.close();
        return Response.ok(result).build();
    }

    @POST
    @Path("upgradeSupportAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String upgradeSupportAdmin(Admin admin) {
        String result = managementAdmin.upgradeSupportAdmin(admin);
        managementAdmin.close();
        return result;
    }

    @POST
    @Path("downgradeSupport")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String downgradeSupport(Admin admin) {
        String result = managementAdmin.downgradeSupport(admin);
        managementAdmin.close();
        return result;
    }

    @GET
    @Path("loadAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response loadAdmin(@BeanParam Agency agency) {

        if(agency.getCodicePartitaIVA().equals("")){
            return Response.ok("{\"code\":-1, \"error\": \"codicePartitaIVA parameter is required\"}").build();
        }


        String result = managementAdmin.getAdmins(agency);
        managementAdmin.close();
        return Response.ok(result).build();
    }
}
