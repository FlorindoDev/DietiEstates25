package org.adm.MainApp;

import org.adm.MainApp.Interfacce.ManagmentAdminService;
import org.adm.Validetor.Validate;
import org.dao.Interfacce.AdminDAO;
import org.dao.postgre.AdminPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.md.Utente.Utente;
import java.util.ArrayList;
import java.util.List;


public class ManagementAdmin implements ManagmentAdminService {

    private AdminDAO adminDAO = new AdminPostgreDAO();

    @Override
    public String addAdmin(Admin admin) {
        //TODO implementa
        Validate validaitor = Validate.getInstance();


        try{
            validaitor.validateName(admin.getNome());
            validaitor.validateCognome(admin.getCognome());
            validaitor.validateEmail(admin.getEmail());
            validaitor.validatePassword(admin.getPassword());

            /*
            if(signup.isUserAbsent(user)){
                validaitor.validateEmail(user.getEmail());
                validaitor.validatePassword(user.getPassword());
                validaitor.validateGenerality(user.getNome(), user.getCognome());
                signup.createUser(user);
                //TODO FARE un oggetto per messaggio di buon fine
                return "{\"code\": 0, \"message\": \"success of action SignUp\"}";
            }
            */

        }catch(DietiEstateException e){
            return e.getMessage();
        }

        return "";


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
