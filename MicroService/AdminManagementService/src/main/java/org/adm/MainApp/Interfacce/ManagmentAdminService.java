package org.adm.MainApp.Interfacce;

import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.md.Utente.Utente;


import java.util.ArrayList;

public interface ManagmentAdminService {

    public void addAdmin(Admin admin);

    public void removeAdmin(Admin admin);

    public void upgradeSupportAdmin(Admin admin);

    public void downgradeSupport(Admin admin);

    public ArrayList<Utente> getAdmins(Agency agency);

}
