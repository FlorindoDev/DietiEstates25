package org.ma.MainApp;

import org.dao.postgre.AcquirentePostgreDAO;
import org.dao.postgre.AdminPostgreDAO;
import org.dao.postgre.AgentPostgreDAO;
import org.dao.postgre.UtentePostgreDAO;
import org.exc.DataBaseException.DietiEstateDBexception;
import org.exc.DataBaseException.UserAlreadyExists;
import org.exc.DietiEstateException;
import org.ma.MainApp.Interface.ManagementAccountService;
import org.ma.Validator.Interface.Validator;
import org.ma.Validator.Validate;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

public class ManagementAccount implements ManagementAccountService {

    private Validator validator;

    public ManagementAccount(){
        validator = Validate.getInstance();
    }

    @Override
    public String applyChangeAcquirente(Acquirente utente) {

        AcquirentePostgreDAO dao = new AcquirentePostgreDAO();
//        System.out.println(utente.Translate());
        try {
            dao.isUserPresent(utente);

            if (!(utente.getPassword() == null)) {
                validator.validatePassword(utente.getPassword());
            }
            if (!(utente.getCognome() == null)) {
                validator.ValidateName(utente.getNome());
            }
            if (!(utente.getNome() == null)) {
                validator.ValidateName(utente.getNome());
            }

//            utente.getNotify_new_estate(); non devono essere controllati
//            utente.getChange_price_notify()

            dao.updateUser(utente);

            return "{\"code\": 0, \"message\": \"success of update action ManagementAccount\"}";
        } catch (DietiEstateException e) {
            return e.getMessage();
        }


    }

    @Override
    public String applyChangeAgent(Agent utente) {
        return null;
    }

    @Override
    public String applyChangeAdmin(Admin utente) {
        return null;
    }

    @Override
    public String getAccountAcquirente(Acquirente utente) {
        try {
            return new AcquirentePostgreDAO().getUser(utente).Translate();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getAccountAdmin(Admin utente) {
        try {
            return new AdminPostgreDAO().getUser(utente).Translate();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getAccountAgent(Agent utente) {
        try {
            return new AgentPostgreDAO().getUser(utente).Translate();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

}
