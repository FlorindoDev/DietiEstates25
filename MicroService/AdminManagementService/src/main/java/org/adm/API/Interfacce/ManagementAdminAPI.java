package org.adm.API.Interfacce;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.core.Response;
import org.md.Agency.Agency;
import org.md.Utente.Admin;



public interface ManagementAdminAPI {

    public String addAdmin(Admin admin);

    public Response removeAdmin(Admin admin);

    public String upgradeSupportAdmin(Admin admin);

    public String downgradeSupport(Admin admin);

    public Response loadAdmin(@BeanParam Agency agency);


}
