package org.email.JakartaEmail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EmailSenderForNotify extends  EmailSenderJakarta{

    public static final String PREFISSO_EMAIL = "notify";

    private String htmlContent;

    protected static final Logger logger = Logger.getLogger(EmailSenderForNotify.class.getName());

    public EmailSenderForNotify () {
        super();
    }

    @Override
    public void sendEmail(String reciverEmail) {

        try {

            // Creazione del messaggio
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(PREFISSO_EMAIL + email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(reciverEmail));
            message.setSubject("Notifica appuntamento");



            message.setContent(htmlContent, "text/html; charset=utf-8");


            Transport.send(message);
            logger.info("[!] Invio riuscito");


        } catch (MessagingException e) {
            logger.severe("[-] Errore nel invio del email:" + e.getMessage());
        }

    }


    @Override
    public void setHtmlContent(String message) {

        InputStream inputStream = EmailSenderJakarta.class.getResourceAsStream("/NotificaAppuntamento.html");

        htmlContent = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                .lines()
                .collect(Collectors.joining("\n"));


        htmlContent = htmlContent + " <td style=\"padding:18px 0px 18px 0px; line-height:22px; text-align:inherit;\" height=\"100%\" valign=\"top\" bgcolor=\"\" role=\"module-content\"><div><div style=\"font-family: inherit; text-align: inherit\"><span style=\"font-size: 18px\">lo stato del tuo appuntamento è passato a </span><span style=\"color: #00aaff; font-size: 18px\">" + message + "</span></div><div></div></div></td>\n" +
                "      </center>\n" +
                "    </body>\n" +
                "  </html>";

    }
}
