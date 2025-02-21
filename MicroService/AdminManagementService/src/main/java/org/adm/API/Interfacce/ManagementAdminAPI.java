package org.adm.API.Interfacce;

import org.adm.API.Resource.ManagementAdminJSON;
import org.md.Utente.Admin;
import org.md.Agency.Agency;


public interface ManagementAdminAPI {

    public String addAdmin(Admin admin);

    public String removeAdmin(Admin admin);

    public String upgradeSupportAdmin(ManagementAdminJSON json);

    public String downgradeSupport(ManagementAdminJSON json);

    public String loadAdmin(ManagementAdminJSON json);


}
