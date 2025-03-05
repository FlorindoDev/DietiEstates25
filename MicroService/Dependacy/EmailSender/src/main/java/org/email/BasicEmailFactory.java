package org.email;


import jakarta.mail.Session;
import org.email.Interfacce.EmailFactory;
import org.exc.DietiEstateException;

import java.util.ArrayList;
import java.util.Properties;

public class BasicEmailFactory implements EmailFactory {

    protected Properties props;
    protected Session session;

    protected String emailFrom;

    public BasicEmailFactory(Properties props, Session session, String email) {
        this.props = props;
        this.session = session;
        this.emailFrom = email;
    }

    @Override
    public Email createEmailNewAgency(ArrayList<String> contents, String nameFileHtml,String receiverEmail) throws DietiEstateException {

        Email email = new Email();
        email.createMessage(session);
        email.setReceiverEmail(receiverEmail);
        email.setSubject("Creazione nuova agenzia");
        email.setFrom("info", emailFrom);
        email.setHtmlContent(contents,nameFileHtml);

        return email;
    }

    @Override
    public Email createEmailAppointment(ArrayList<String> contents, String nameFileHtml,String receiverEmail) throws DietiEstateException {

        Email email = new Email();
        email.createMessage(session);
        email.setReceiverEmail(receiverEmail);
        email.setSubject("Stato appuntamento aggiornato");
        email.setFrom("notify", emailFrom);
        email.setHtmlContent(contents,nameFileHtml);

        return email;
    }

    @Override
    public Email createEmailEstate() {
        return null;
    }
}
