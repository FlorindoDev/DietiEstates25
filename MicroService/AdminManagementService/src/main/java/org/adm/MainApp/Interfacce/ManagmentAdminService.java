package org.adm.MainApp.Interfacce;

import org.md.Agency.Agency;
import org.md.Utente.Admin;

public interface ManagmentAdminService {

    public String addAdmin(Admin admin);

    public String removeAdmin(Admin admin);

    public String upgradeSupportAdmin(Admin admin);

    public String downgradeSupport(Admin admin);

    public String getAdmins(Agency agency);

    void close();

}
