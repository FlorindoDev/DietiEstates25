package org.adm.API.Interfacce;

import org.md.Agency.Agency;
import org.md.Utente.Admin;



public interface ManagementAdminAPI {

    public String addAdmin(Admin admin);

    public String removeAdmin(Admin admin);

    public String upgradeSupportAdmin(Admin admin);

    public String downgradeSupport(Admin admin);

    public String loadAdmin(Agency agency);


}
