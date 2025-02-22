package org.agm.MainApp;

import org.agm.MainApp.Interfacce.ManagmentAgentService;
import org.agm.Validetor.Validate;
import org.dao.Interfacce.AgencyDAO;
import org.dao.Interfacce.AgentDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.dao.postgre.AgentPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

import java.util.ArrayList;

public class ManagementAgent implements ManagmentAgentService {

    private AgentDAO agentDAO = new AgentPostgreDAO();
    private AgencyDAO agencyDAO = new AgencyPostgreDAO();

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
            //adminDAO.isUserPresent(admin);

            ArrayList<Agent> agents = agencyDAO.getAgents(agency);
            String json = "{\"code\": 0, \"message\": \"success of action admin upgraded\", \"admins\": [{";

            for (Agent agent : agents){
                json = json.concat(agent.TranslateToJson());
                if(!agent.equals(agents.getLast()))
                    json = json.concat(",");
            }


            json = json + "}]}";

            System.out.println(json);
            return json;
        }catch (DietiEstateException e){
            return e.getMessage();
        }
    }

    @Override
    public String getEstates(Agency agency) {
        try{
            //adminDAO.isUserPresent(admin);

            ArrayList<Estate> estates = agencyDAO.getEstates(agency);
            String json = "{\"code\": 0, \"message\": \"success of action admin upgraded\", \"admins\": [{";

            for (Estate estate : estates){
                json = json.concat(estate.TranslateToJson());
                if(!estate.equals(estates.getLast()))
                    json = json.concat(",");
            }


            json = json + "}]}";

            System.out.println(json);
            return json;
        }catch (DietiEstateException e){
            return e.getMessage();
        }
    }
}
