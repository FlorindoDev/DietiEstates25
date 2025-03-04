package org.email;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.email.JakartaEmail.EmailSenderJakarta;
import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.FailSendNotify;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Email {

    public static final String SWAP_MARKER = "swap";
    private Message message;

    private String htmlContent;

    public Email() {
        //per costruirlo
    }

    public Email(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return this.message;
    }

    public void createMessage(Session session) {
        if(this.message == null) this.message = new MimeMessage(session);
    }

    public void setReceiverEmail(String reciverEmail) throws DietiEstateException {
        if(this.message == null) return;

        try {
            this.message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(reciverEmail));
        } catch (Exception e) {
            throw new FailSendNotify(e.getMessage());
        }


    }

    public void setFrom(String prefix, String email) throws DietiEstateException {
        if(this.message == null) return;

        try {
            this.message.setFrom(new InternetAddress(prefix + email));
        } catch (Exception e) {
            throw new FailSendNotify(e.getMessage());
        }
    }


    public void setSubject(String oggetto) throws DietiEstateException {
        if(this.message == null) return;

        try {
            this.message.setSubject(oggetto);
        } catch (MessagingException e) {
            throw new FailSendNotify(e.getMessage());
        }
    }

    public void setSession(Session session){
        this.message = new MimeMessage(session);
    }


    public String getHtmlContent() {
        return htmlContent;
    }


    public void setHtmlContent(List<String> contents, String nameFileHtml) throws DietiEstateException {

        try{

            InputStream inputStream = EmailSenderJakarta.class.getResourceAsStream(nameFileHtml);

            htmlContent = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                    .lines()
                    .collect(Collectors.joining("\n"));

            for(String content : contents){
                htmlContent = htmlContent.replaceFirst(SWAP_MARKER, content);
            }

            this.message.setContent(htmlContent, "text/html; charset=utf-8");

        }catch (Exception e){
            throw new FailSendNotify("File html non trovato");
        }



    }

}
