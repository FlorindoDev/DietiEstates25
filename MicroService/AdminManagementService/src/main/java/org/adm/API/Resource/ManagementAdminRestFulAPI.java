package org.adm.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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

    @POST
    @Path("removeAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String removeAdmin(Admin admin) {
        String result = managementAdmin.removeAdmin(admin);
        managementAdmin.close();
        return result;
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

    @POST
    @Path("loadAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String loadAdmin(Agency agency) {
        String result = managementAdmin.getAdmins(agency);
        managementAdmin.close();
        return result;
    }
}
