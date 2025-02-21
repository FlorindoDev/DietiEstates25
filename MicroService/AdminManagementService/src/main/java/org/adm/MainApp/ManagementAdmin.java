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


public class ManagementAdmin implements ManagmentAdminService {

    private AdminDAO adminDAO = new AdminPostgreDAO();

    @Override
    public String addAdmin(Admin admin, Agency agency) {

        try{
            adminDAO.isUserAbsent(admin);
        }catch (DietiEstateException e){
            return e.getMessage();
        }

        //TODO implementa
        Validate validaitor = Validate.getInstance();


        try{
            validaitor.validateName(admin.getNome());
            validaitor.validateCognome(admin.getCognome());
            validaitor.validateEmail(admin.getEmail());
            validaitor.validatePassword(admin.getPassword());

            adminDAO.createUser(admin);

            //TODO FARE un oggetto per messaggio di buon fine
            return "{\"code\": 0, \"message\": \"success of action admin create\"}";


        }catch(DietiEstateException e){
            return e.getMessage();
        }


    }


    @Override
    public String removeAdmin(Admin admin) {
        try{
            adminDAO.removeAdmin(admin);
            return "{\"code\": 0, \"message\": \"success of action admin remove\"}";
        }catch (DietiEstateException e){
            return e.getMessage();
        }


    }

    @Override
    public String upgradeSupportAdmin(Admin admin) {
        try{
            adminDAO.isUserPresent(admin);

            adminDAO.upgradeSupportAdmin(admin);
            return "{\"code\": 0, \"message\": \"success of action admin upgraded\"}";
        }catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String downgradeSupport(Admin admin) {
        try{
            adminDAO.isUserPresent(admin);

            adminDAO.downgradeSupport(admin);
            return "{\"code\": 0, \"message\": \"success of action admin upgraded\"}";
        }catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public ArrayList<Utente> getAdmins(Agency agency) {
        //TODO implementa
        return null;
    }

}
