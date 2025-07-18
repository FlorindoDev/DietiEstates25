package org.rab.Resource.NotifySenders;


import org.dao.Interfacce.EstateDAO;
import org.dao.Interfacce.NotifyDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.dao.postgre.NotifyPostgreDAO;
import org.email.Email;
import org.email.Interfacce.EmailSender;
import org.email.JakartaEmail.EmailSenderJakarta;
import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.FailSendNotify;
import org.md.Notify.AppuntamentoAccettato;
import org.md.Notify.AppuntamentoDaConfermare;
import org.md.Notify.AppuntamentoRifiutato;
import org.md.Notify.Notify;
import org.md.Utente.Agent;

import java.util.ArrayList;

public class NotifySenders {


    public void sendNotify(AppuntamentoAccettato notification) throws DietiEstateException {
        sendNotify((Notify)notification);
    }

    public void sendNotify(AppuntamentoRifiutato notification) throws DietiEstateException {
        sendNotify((Notify)notification);
    }

    public void sendNotify(AppuntamentoDaConfermare notification) throws DietiEstateException {

        try {
            EmailSender emailSender = new EmailSenderJakarta();
            Agent agent = getAgent(notification);

            if(!agent.getNotifyAppointment()){
                System.out.println("[!] Utente non vuole notifiche invio non fatto");
                return;
            }

            String emailUser = agent.getEmail();

            createNotifyOnDB(notification);

            Email email = getEmail(notification, emailSender, emailUser,"/NotificaAppuntamentoPending.html");

            emailSender.sendEmail(email);

        }catch (Exception e) {
            throw  new FailSendNotify(e.getMessage());
        }

    }


    public void sendNotify(Notify notification) throws DietiEstateException {
        try {

            EmailSender emailSender = new EmailSenderJakarta();
            String emailUser = notification.getAcquirente().getEmail();

            createNotifyOnDB(notification);


            Email email = getEmail(notification, emailSender, emailUser,"/NotificaAppuntamento.html");

            emailSender.sendEmail(email);

        } catch (Exception e) {
            throw  new FailSendNotify(e.getMessage());
        }


    }

    private Agent getAgent(AppuntamentoDaConfermare notification) throws DietiEstateException {
        EstateDAO estateDAO = new EstatePostgreDAO();
        Agent agent = estateDAO.getAgent(notification.getEstate());
        estateDAO.close();
        return agent;
    }

    private void createNotifyOnDB(Notify notification) throws DietiEstateException {
        NotifyDAO notifyDAO = new NotifyPostgreDAO();
        notifyDAO.createNotify(notification);
        notifyDAO.close();
    }

    private Email getEmail(Notify notification, EmailSender senderEmail, String emailUser,String fileNameHtml) throws DietiEstateException {

        ArrayList<String> contents = new ArrayList<>();


        contents.add(notification.getTipo());
        contents.add(notification.getData());


        Email email = null;
        email = senderEmail.getFacotryEmail().createEmailAppointment(contents, fileNameHtml, emailUser);
        return email;
    }
}
