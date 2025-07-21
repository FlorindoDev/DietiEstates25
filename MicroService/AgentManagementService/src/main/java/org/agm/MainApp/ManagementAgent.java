package org.agm.MainApp;

import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.agm.MainApp.Interfacce.ManagmentAgentService;
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
            validaitor.validatePartitaIVA(agent.getAgency().getCodicePartitaIVA());

            agentDAO.createUser(agent);

            //TODO FARE un oggetto per messaggio di buon fine
            return "{\"code\": 0, \"message\": \"success of action agent create\"}";


        }catch(DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String removeAgent(Agent agent) {

        try{
            Validator validaitor = Validate.getInstance();
            validaitor.validateEmail(agent.getEmail());
            agentDAO.removeAgent(agent);
            return "{\"code\": 0, \"message\": \"success of action admin remove\"}";
        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }

    @Override
    public String getAgents(Agency agency) {
        try{
            Validator validaitor = Validate.getInstance();
            validaitor.validatePartitaIVA(agency.getCodicePartitaIVA());

            agencyDAO.isAgencyPresent(agency);

            List<Agent> agents = agencyDAO.getAgents(agency);
            String json = "{\"code\": 0, \"message\": \"success of action get agent\", \"agents\": [";

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
            Validator validaitor = Validate.getInstance();
            validaitor.validatePartitaIVA(agency.getCodicePartitaIVA());

            agencyDAO.isAgencyPresent(agency);

            List<Estate> estates = agencyDAO.getEstates(agency);
            String json = "{\"code\": 0, \"message\": \"success of action get estate\", \"Estates\": [";

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
    public String updateEstateAgent(Estate estate, Agent agent) {
        try{
            Validator validaitor = Validate.getInstance();
            validaitor.validateEmail(agent.getEmail());

            estateDAO.isEstatePresent(estate);
            agentDAO.isUserPresent(agent);
            agent = agentDAO.estraiIdFromEmailAgent(agent);
            estateDAO.updateEstateAgent(estate,agent);

            return "{\"code\": 0, \"message\": \"success of action add agent to estate\"}";
        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }

    @Override
    public void close() {
        agencyDAO.close();
        agentDAO.close();
        estateDAO.close();
    }

    public void setAgentDAO(AgentDAO agentDAO) {
        this.agentDAO.close();
        this.agentDAO = agentDAO;
    }
}
