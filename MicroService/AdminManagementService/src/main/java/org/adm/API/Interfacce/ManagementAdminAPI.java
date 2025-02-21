package org.adm.API.Interfacce;

import org.adm.API.Resource.ManagementAdminJSON;
import org.md.Utente.Admin;


public interface ManagementAdminAPI {

    public String addAdmin(Admin admin);

    public String removeAdmin(ManagementAdminJSON json);

    public String upgradeSupportAdmin(ManagementAdminJSON json);

    public String downgradeSupport(ManagementAdminJSON json);

    public String loadAdmin(ManagementAdminJSON json);


}
