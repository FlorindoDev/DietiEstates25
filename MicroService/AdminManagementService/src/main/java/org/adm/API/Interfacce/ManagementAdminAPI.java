package org.adm.API.Interfacce;

import jakarta.ws.rs.Path;
import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.md.Utente.Utente;

import java.util.ArrayList;


public interface ManagementAdminAPI {

    public String addAdmin(Admin admin);

    public String removeAdmin(Admin admin);

    public String upgradeSupportAdmin(Admin admin);

    public String downgradeSupport(Admin admin);

    public String loadAdmin(Agency agency);


}
