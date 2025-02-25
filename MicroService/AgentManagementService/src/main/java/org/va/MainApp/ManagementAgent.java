package org.va.MainApp;

import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.va.MainApp.Interfacce.ManagmentAgentService;
import org.va.Validate;
import org.dao.Interfacce.AgencyDAO;
import org.dao.Interfacce.AgentDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.dao.postgre.AgentPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;
import org.va.Validator;

import java.util.List;

public class ManagementAgent implements ManagmentAgentService {

    private AgentDAO agentDAO = new AgentPostgreDAO();
    private AgencyDAO agencyDAO = new AgencyPostgreDAO();

    private EstateDAO estateDAO = new EstatePostgreDAO();

    @Override
    public String addAgent(Agent agent) {
        try{
            agentDAO.isUserAbsentOverAll(agent);
        }catch (DietiEstateException e){
            return e.getMessage();
        }

        Validator validaitor = Validate.getInstance();


        try{
            validaitor.validateName(agent.getNome());
            validaitor.validateCognome(agent.getCognome());
            validaitor.validateEmail(agent.getEmail());
            validaitor.validatePassword(agent.getPassword());

            agentDAO.createUser(agent);

            //TODO FARE un oggetto per messaggio di buon fine
            return "{\"code\": 0, \"message\": \"success of action admin create\"}";


        }catch(DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String removeAgent(Agent agent) {

        try{
            agentDAO.removeAdmin(agent);
            return "{\"code\": 0, \"message\": \"success of action admin remove\"}";
        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }

    @Override
    public String getAgents(Agency agency) {
        try{
            //TODO agencyDAO.isAgencyPresent(admin);

            List<Agent> agents = agencyDAO.getAgents(agency);
            String json = "{\"code\": 0, \"message\": \"success of action get agent\", \"Agent\": [";

            for (Agent agent : agents){
                json = json.concat(agent.TranslateToJson());
                if(!agent.equals(agents.getLast()))
                    json = json.concat(",");
            }


            json = json + "]}";

            return json;
        }catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getEstates(Agency agency) {
        try{
            //TODO agencyDAO.isAgencyPresent(admin);

            List<Estate> estates = agencyDAO.getEstates(agency);
            String json = "{\"code\": 0, \"message\": \"success of action get estate\", \"Estate\": [";

            for (Estate estate : estates){
                json = json.concat(estate.TranslateToJson());
                if(!estate.equals(estates.getLast()))
                    json = json.concat(",");
            }


            json = json + "]}";

            return json;
        }catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String addEstateAgent(Estate estate, Agent agent) {
        try{
            estateDAO.IsEstatePresent(estate);
            agentDAO.isUserPresent(agent);
            estateDAO.addEstateAgent(estate,agent);

            return "{\"code\": 0, \"message\": \"success of action add agent to estate\"}";
        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }

    @Override
    public String removeEstateAgent(Estate estate, Agent agent) {
        return addEstateAgent(estate,agent);
    }
}
