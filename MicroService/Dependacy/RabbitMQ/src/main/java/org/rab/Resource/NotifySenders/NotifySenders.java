package org.rab.Resource.NotifySenders;


import org.dao.Interfacce.NotifyDAO;
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

import java.util.ArrayList;

public class NotifySenders {


    public void sendNotify(AppuntamentoAccettato notification) throws DietiEstateException {
        sendNotify((Notify)notification);
    }

    public void sendNotify(AppuntamentoRifiutato notification) throws DietiEstateException {
        sendNotify((Notify)notification);
    }

    public void sendNotify(AppuntamentoDaConfermare notification) throws DietiEstateException {
        //TODO


    }

    public void sendNotify(Notify notification) throws DietiEstateException {
        try {

            NotifyDAO notifyDAO = new NotifyPostgreDAO();
            EmailSender senderEmail = new EmailSenderJakarta();

            notifyDAO.createNotify(notification);

            ArrayList<String> contents = new ArrayList<>();

            contents.add(notification.getTipo());
            contents.add(notification.getData());

            String emailUser = notification.getAcquirente().getEmail();


            Email email = null;
            email = senderEmail.getFacotryEmail().createEmailAppointment(contents,"/NotificaAppuntamento.html",emailUser);
            senderEmail.sendEmail(email);

        } catch (Exception e) {
            throw  new FailSendNotify(e.getMessage());
        }





    }
}
