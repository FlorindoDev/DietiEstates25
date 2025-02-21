package org.ma.MainApp;

import org.dao.postgre.AcquirentePostgreDAO;
import org.dao.postgre.AdminPostgreDAO;
import org.dao.postgre.AgentPostgreDAO;
import org.dao.postgre.UtentePostgreDAO;
import org.exc.DataBaseException.DietiEstateDBexception;
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
    public void applyChangeAcquirente(Acquirente utente) {

    }

    @Override
    public void applyChangeAgent(Agent utente) {

    }

    @Override
    public void applyChangeAdmin(Admin utente) {

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
