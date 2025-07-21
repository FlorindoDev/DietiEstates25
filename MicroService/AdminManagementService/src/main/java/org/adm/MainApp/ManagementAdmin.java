package org.adm.MainApp;

import org.adm.MainApp.Interfacce.ManagmentAdminService;
import org.va.Validate;
import org.dao.Interfacce.AdminDAO;
import org.dao.Interfacce.AgencyDAO;
import org.dao.postgre.AdminPostgreDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.va.Validator;


import java.util.List;


public class ManagementAdmin implements ManagmentAdminService {

    private AdminDAO adminDAO = new AdminPostgreDAO();
    private AgencyDAO agencyDAO = new AgencyPostgreDAO();

    @Override
    public String addAdmin(Admin admin) {

        try{
            adminDAO.isUserAbsentOverAll(admin);
        }catch (DietiEstateException e){
            return e.getMessage();
        }

        Validator validaitor = Validate.getInstance();


        try{
            validaitor.validateName(admin.getNome());
            validaitor.validateCognome(admin.getCognome());
            validaitor.validateEmail(admin.getEmail());
            validaitor.validatePassword(admin.getPassword());
            validaitor.validatePartitaIVA(admin.getAgency().getCodicePartitaIVA());


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
            Validator validaitor = Validate.getInstance();
            validaitor.validateEmail(admin.getEmail());

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
            Validator validaitor = Validate.getInstance();
            validaitor.validateEmail(admin.getEmail());

            adminDAO.isUserPresent(admin);

            adminDAO.downgradeSupport(admin);
            return "{\"code\": 0, \"message\": \"success of action admin downgrade\"}";
        }catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getAdmins(Agency agency) {
        try{
            Validator validaitor = Validate.getInstance();
            validaitor.validatePartitaIVA(agency.getCodicePartitaIVA());

            agencyDAO.isAgencyPresent(agency);

            List<Admin> admins = agencyDAO.getAdmins(agency);
            String json = "{\"code\": 0, \"message\": \"success of action load admin\", \"admins\": [";

            for (Admin admin : admins){
                json = json.concat(admin.TranslateToJson());
                if(!admin.equals(admins.getLast()))
                    json = json.concat(",");
            }


            json = json + "]}";

            return json;
        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }

    public void close(){
        adminDAO.close();
        agencyDAO.close();
    }

    public void setAdminDAO(AdminDAO adminDAO) {
        this.adminDAO.close();
        this.adminDAO = adminDAO;
    }
}
