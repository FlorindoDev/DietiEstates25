package org.adm.API.Interfacce;

import org.adm.API.Resource.ManagementAdminJSON;


public interface ManagementAdminAPI {

    public String addAdmin(ManagementAdminJSON json);

    public String removeAdmin(ManagementAdminJSON json);

    public String upgradeSupportAdmin(ManagementAdminJSON json);

    public String downgradeSupport(ManagementAdminJSON json);

    public String loadAdmin(ManagementAdminJSON json);


}
