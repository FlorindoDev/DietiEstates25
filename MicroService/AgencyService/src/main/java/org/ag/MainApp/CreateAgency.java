package org.ag.MainApp;


import org.ag.MainApp.Interfacce.CreateAgencyService;
import org.ag.Validaitor.Validate;
import org.dao.Interfacce.AgencyDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;

public class CreateAgency implements CreateAgencyService {

    AgencyDAO create = new AgencyPostgreDAO();

    public CreateAgency() {}

    @Override
    public String makeAgency(Agency agency) {

        Validate validaitor = Validate.getInstance();

        try{

            validaitor.validateEmail(agency.getEmail());
            validaitor.validateAgencyName(agency.getNome());

            create.createAgency(agency);
            // TODO  AGGIUSATRE RITORNO
            return "{\"code\": 0, \"message\": \"success of action create agency\"}";

        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }
}
