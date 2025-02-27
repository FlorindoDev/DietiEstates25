package org.ag.MainApp;


import org.ag.MainApp.Interfacce.CreateAgencyService;
import org.dao.Interfacce.AdminDAO;
import org.dao.postgre.AdminPostgreDAO;
import org.va.Validate;
import org.dao.Interfacce.AgencyDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.email.Interfacce.EmailSender;
import org.email.JakartaEmail.EmailSenderForNewAgency;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.va.Validator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateAgency implements CreateAgencyService {

    private final AgencyDAO create;
    private final AdminDAO createAdmin;

    public CreateAgency() {
        create = new AgencyPostgreDAO();
        createAdmin = new AdminPostgreDAO();
    }

    @Override
    public String makeAgency(Agency agency) {

        Validator validaitor = Validate.getInstance();

        try{
            create.isAgencyAbsent(agency);

            create.isNameAgencyAbsent(agency);

            validateAgencyField(agency, validaitor);

            create.createAgency(agency);

            //TODO  generare password e aggiungere admin
            sendEmail(agency);


            // TODO  AGGIUSATRE RITORNO
            return "{\"code\": 0, \"message\": \"Success of action create agency\"}";

        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }

    private void sendEmail(Agency agency){

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
                                EmailSender sender = new EmailSenderForNewAgency("prova");
                                sender.sendEmail(agency.getEmail());
        };

        executor.submit(task);


    }

    private void validateAgencyField(Agency agency, Validator validaitor) throws DietiEstateException {
        validaitor.validateEmail(agency.getEmail());
        validaitor.validateAgencyName(agency.getNome());
        validaitor.validatePartitaIVA(agency.getCodicePartitaIVA());
        validaitor.validateSede(agency.getSede());
    }
}
