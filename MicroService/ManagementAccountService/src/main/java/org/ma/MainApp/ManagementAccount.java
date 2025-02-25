package org.ma.MainApp;

import org.dao.postgre.AcquirentePostgreDAO;
import org.dao.postgre.AdminPostgreDAO;
import org.dao.postgre.AgentPostgreDAO;

import org.exc.DietiEstateException;
import org.ma.MainApp.Interface.ManagementAccountService;
import org.ma.Validator.Interface.Validator;
import org.ma.Validator.Validate;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

public class ManagementAccount implements ManagementAccountService {

    public static final String SUCCESS = "{\"code\": 0, \"message\": \"success of update action ManagementAccount\"}";
    private Validator validator;

    public ManagementAccount(){
        validator = Validate.getInstance();
    }

    private void validateData(Utente utente) throws DietiEstateException {
        if (utente.getPassword() != null) {
            validator.validatePassword(utente.getPassword());
        }
        if (utente.getCognome() != null) {
            validator.validateName(utente.getNome());
        }
        if (utente.getNome() != null) {
            validator.validateName(utente.getNome());
        }
    }

    @Override
    public String applyChangeAcquirente(Acquirente utente) {

        AcquirentePostgreDAO dao = new AcquirentePostgreDAO();

        try {
            dao.isUserPresent(utente);

            validateData(utente);

            //TODO contollare path immagine profilo & STOREGE delle stesse

            dao.updateUser(utente);

            return SUCCESS;
        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    @Override
    public String applyChangeAgent(Agent utente) {

        AgentPostgreDAO dao = new AgentPostgreDAO();

        try {
            dao.isUserPresent(utente);

            validateData(utente);

            dao.updateUser(utente);

            return SUCCESS;
        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    @Override
    public String applyChangeAdmin(Admin utente) {

        AdminPostgreDAO dao = new AdminPostgreDAO();

        try {
            dao.isUserPresent(utente);

            validateData(utente);

            dao.updateUser(utente);

            return SUCCESS;
        } catch (DietiEstateException e) {
            return e.getMessage();
        }
    }

    @Override
    public String getAccountAcquirente(Acquirente utente) {
        try {
            return new AcquirentePostgreDAO().getUser(utente).TranslateToJson();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getAccountAdmin(Admin utente) {
        try {
            return new AdminPostgreDAO().getUser(utente).TranslateToJson();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getAccountAgent(Agent utente) {
        try {
            return new AgentPostgreDAO().getUser(utente).TranslateToJson();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

}
