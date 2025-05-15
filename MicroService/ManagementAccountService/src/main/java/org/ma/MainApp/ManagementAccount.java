package org.ma.MainApp;

import org.dao.postgre.AcquirentePostgreDAO;
import org.dao.postgre.AdminPostgreDAO;
import org.dao.postgre.AgentPostgreDAO;
import org.exc.DietiEstateException;
import org.ma.MainApp.Interface.ManagementAccountService;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;
import org.va.Validate;
import org.va.Validator;

public class ManagementAccount implements ManagementAccountService {

    private static final String SUCCESS = "{\"code\": 0, \"message\": \"success of update action ManagementAccount\"}";
    private Validator validator;
    private AgentPostgreDAO agentDAO;
    private AdminPostgreDAO adminDAO;
    private AcquirentePostgreDAO acquirenteDAO;

    public ManagementAccount(){validator = Validate.getInstance();}

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

        acquirenteDAO = new AcquirentePostgreDAO();

        try {
            validateData(utente);

            acquirenteDAO.updateUser(utente);

            return SUCCESS;
        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    @Override
    public String applyChangeAgent(Agent utente) {

        agentDAO = new AgentPostgreDAO();

        try {
            validateData(utente);

            agentDAO.updateUser(utente);

            return SUCCESS;
        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    @Override
    public String applyChangeAdmin(Admin utente) {

        adminDAO = new AdminPostgreDAO();

        try {
            validateData(utente);

            adminDAO.updateUser(utente);

            return SUCCESS;
        } catch (DietiEstateException e) {
            return e.getMessage();
        }
    }

    @Override
    public String getAccountAcquirente(Acquirente utente) {
        try {
            acquirenteDAO = new AcquirentePostgreDAO();
            return acquirenteDAO.getUser(utente).TranslateToJson();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getAccountAdmin(Admin utente) {
        try {
            adminDAO = new AdminPostgreDAO();
            return adminDAO.getUser(utente).TranslateToJson();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getAccountAgent(Agent utente) {
        try {
            agentDAO = new AgentPostgreDAO();
            return agentDAO.getUser(utente).TranslateToJson();
        } catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public void close() {
        if (acquirenteDAO != null) {acquirenteDAO.close();}
        if (agentDAO != null) {agentDAO.close();}
        if (adminDAO != null) {adminDAO.close();}
    }

}
