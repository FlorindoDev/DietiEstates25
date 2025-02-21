package org.adm.MainApp;

import org.adm.MainApp.Interfacce.ManagmentAdminService;
import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.md.Utente.Utente;
import java.util.ArrayList;


public class ManagementAdmin implements ManagmentAdminService {


    @Override
    public String addAdmin(Admin admin) {
        //TODO implementa
        


        return null;
    }


    @Override
    public void removeAdmin(Admin admin) {
        //TODO implementa
    }

    @Override
    public void upgradeSupportAdmin(Admin admin) {
        //TODO implementa
    }

    @Override
    public void downgradeSupport(Admin admin) {
        //TODO implementa
    }

    @Override
    public ArrayList<Utente> getAdmins(Agency agency) {
        //TODO implementa
        return null;
    }
}
