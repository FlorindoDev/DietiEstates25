package org.ag.MainApp;


import org.ag.MainApp.Interfacce.CreateAgencyService;
import org.exc.DietiEstateMicroServiceException.ErrorCreatingAgency;
import org.md.Utente.Admin;
import org.va.Validate;
import org.dao.Interfacce.AgencyDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.email.Interfacce.EmailSender;
import org.email.JakartaEmail.EmailSenderForNewAgency;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.va.Validator;


import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateAgency implements CreateAgencyService {

    public static final String CHARACTERS_FOR_GENERATE_PASSWORD = "Z5v!EeR9aFGdySO$fcgDu4Wpi8xVo2N1tXClAnsbz6BTrYQwLm_3IjPHKkqhM0UJ7";
    private final AgencyDAO create;

    private final SecureRandom random = new SecureRandom();

    public CreateAgency() {
        create = new AgencyPostgreDAO();
    }

    @Override
    public String makeAgency(Agency agency) {

        Validator validaitor = Validate.getInstance();

        try{

            validateAgencyField(agency, validaitor);

            if(agency.getAdmins() == null) throw new ErrorCreatingAgency();
            Admin admin = initAdmin(agency);

            create.createAgencyAtomic(agency);

            sendEmail(admin);


            // TODO  AGGIUSATRE RITORNO
            return "{\"code\": 0, \"message\": \"Success of action create agency\"}";

        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }

    private Admin initAdmin(Agency agency) {
        Admin admin = agency.getAdmins().getFirst();
        admin.setPassword(generateRandomWord(10));
        admin.setAgency(agency);
        admin.setNome("admin");
        admin.setCognome("admin");
        admin.setSupport(false);
        return admin;
    }

    private void sendEmail(Admin admin){

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
                                EmailSender sender = new EmailSenderForNewAgency(admin.getPassword());
                                sender.sendEmail(admin.getEmail());
        };

        executor.submit(task);


    }

    private String generateRandomWord(int length) {

        String characters = CHARACTERS_FOR_GENERATE_PASSWORD;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private void validateAgencyField(Agency agency, Validator validaitor) throws DietiEstateException {

        String emailAdmin = agency.getAdmins().getFirst().getEmail();

        validaitor.validateEmail(emailAdmin);
        validaitor.validateAgencyName(agency.getNome());
        validaitor.validatePartitaIVA(agency.getCodicePartitaIVA());
        validaitor.validateSede(agency.getSede());
    }
}
