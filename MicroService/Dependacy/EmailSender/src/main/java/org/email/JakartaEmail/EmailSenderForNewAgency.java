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
    public void SendEmail(String reciverEmail) {
        try {
            // Creazione del messaggio
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciverEmail));
            message.setSubject("Crezione Agenzia effettuata!!");
            message.setText("bravo");

            // Invio della email
            Transport.send(message);


        } catch (MessagingException e) {
            logger.severe("[-] Errore nel invio del email:" + e.getMessage());
        }

    }
}
