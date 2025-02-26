package org.email.JakartaEmail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailSenderForNewAgency extends EmailSenderJakarta{
    public EmailSenderForNewAgency() {
        super();
    }

    @Override
    public void sendEmail(String reciverEmail) {
        try {
            // Creazione del messaggio
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("DietiEstate@demomailtrap.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(reciverEmail));
            message.setSubject("Oggetto della Email");
            message.setText("Questo è il corpo della email inviata tramite JavaMail.");

            // Invio del messaggio
            Transport.send(message);


        } catch (MessagingException e) {
            logger.severe("[-] Errore nel invio del email:" + e.getMessage());
        }

    }
}
