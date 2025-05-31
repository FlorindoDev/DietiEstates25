package org.adm.API.Interfacce;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.core.Response;
import org.md.Agency.Agency;
import org.md.Utente.Admin;



public interface ManagementAdminAPI {

    public Response addAdmin(Admin admin);

    public Response removeAdmin(Admin admin);

    public Response upgradeSupportAdmin(Admin admin);

    public Response downgradeSupportAdmin(Admin admin);

    public Response loadAdmin(@BeanParam Agency agency);


}
