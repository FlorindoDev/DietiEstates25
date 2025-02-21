package org.adm.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.adm.API.Interfacce.ManagementAdminAPI;
import org.adm.MainApp.Interfacce.ManagmentAdminService;
import org.adm.MainApp.ManagementAdmin;
import org.exc.DietiEstateException;
import org.md.Utente.Admin;

@Path("ManagementAdmin")
public class ManagementAdminRestFulAPI implements ManagementAdminAPI {

    ManagmentAdminService managementAdmin =  new ManagementAdmin();

    @POST
    @Path("addAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String addAdmin(Admin admin) {

        return managementAdmin.addAdmin(admin,admin.getAgency());
    }

    @POST
    @Path("removeAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String removeAdmin(ManagementAdminJSON json) {

        //TODO implementa
        return null;
    }

    @POST
    @Path("upgradeSupportAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String upgradeSupportAdmin(ManagementAdminJSON json) {
        //TODO implementa
        return null;
    }

    @POST
    @Path("downgradeSupport")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String downgradeSupport(ManagementAdminJSON json) {
        //TODO implementa
        return null;
    }

    @POST
    @Path("loadAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String loadAdmin(ManagementAdminJSON json) {

        //TODO implementa
        return null;
    }
}
