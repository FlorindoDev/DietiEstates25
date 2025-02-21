package org.ag.MainApp;


import org.ag.MainApp.Interfacce.CreateAgencyService;
import org.ag.Validaitor.Validate;
import org.dao.Interfacce.AgencyDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.email.Interfacce.EmailSender;
import org.email.JakartaEmail.EmailSenderForNewAgency;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;

public class CreateAgency implements CreateAgencyService {

    private AgencyDAO create = new AgencyPostgreDAO();

    public CreateAgency() {}

    @Override
    public String makeAgency(Agency agency) {


        Validate validaitor = Validate.getInstance();

        EmailSender sender = new EmailSenderForNewAgency();


        try{
            if(create.isAgencyAbsent(agency) && create.isNameAgencyAbsent(agency)){


                validaitor.validateEmail(agency.getEmail());
                validaitor.validateAgencyName(agency.getNome());
                validaitor.validatePartitaIVA(agency.getCodice_partitaIVA());
                validaitor.validateSede(agency.getSede());

                create.createAgency(agency);
                // TODO  AGGIUNGERE EMAILSENDER
                //sender.SendEmail(agency.getEmail());

                // TODO  AGGIUSATRE RITORNO
                return "{\"code\": 0, \"message\": \"Success of action create agency\"}";
            }
        }catch (DietiEstateException e){
            return e.getMessage();
        }
        return "";
    }
}
