package org.agm.MainApp;

import org.agm.MainApp.Interfacce.ManagmentAgentService;
import org.agm.Validetor.Validate;
import org.dao.Interfacce.AgentDAO;
import org.dao.postgre.AgentPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Utente.Agent;

public class ManagementAgent implements ManagmentAgentService {

    private AgentDAO agentDAO = new AgentPostgreDAO();

    @Override
    public String addAgent(Agent agent) {
        try{
            agentDAO.isUserAbsent(agent);
        }catch (DietiEstateException e){
            return e.getMessage();
        }

        //TODO implementa
        Validate validaitor = Validate.getInstance();


        try{
            validaitor.validateName(agent.getNome());
            validaitor.validateCognome(agent.getCognome());
            validaitor.validateEmail(agent.getEmail());
            validaitor.validatePassword(agent.getPassword());

            agent.createUser(agent);

            //TODO FARE un oggetto per messaggio di buon fine
            return "{\"code\": 0, \"message\": \"success of action admin create\"}";


        }catch(DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String removeAgent(Agent agent) {
        return null;
    }
}
