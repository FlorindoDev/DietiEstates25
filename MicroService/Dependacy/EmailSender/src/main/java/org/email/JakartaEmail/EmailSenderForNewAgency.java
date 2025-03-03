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

public class EmailSenderForNewAgency extends EmailSenderJakarta{

    public static final String PREFISSO_EMAIL = "info";

    private final String password;

    private String htmlContent;

    protected static final Logger logger = Logger.getLogger(EmailSenderForNewAgency.class.getName());

    public EmailSenderForNewAgency(String password) {
        super();
        this.password = password;
    }

    @Override
    public void sendEmail(String reciverEmail) {

        try {

            // Creazione del messaggio
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(PREFISSO_EMAIL + email));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(reciverEmail));
            message.setSubject("Creazione Agenzia");

            setHtmlContent(reciverEmail);

            message.setContent(htmlContent, "text/html; charset=utf-8");


            Transport.send(message);
            logger.info("[!] Invio riuscito");


        } catch (MessagingException e) {
            logger.severe("[-] Errore nel invio del email:" + e.getMessage());
        }

    }

    @Override
    public void setHtmlContent(String reciverEmail) {

        InputStream inputStream = EmailSenderJakarta.class.getResourceAsStream("/creazioneAgenzia.html");

        htmlContent = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                .lines()
                .collect(Collectors.joining("\n"));


        htmlContent = htmlContent + "    <center>\n" +
                "    <li style=\"text-align: inherit; font-size: 18px; font-size: 18px\"><span style=\"color: #000000; font-family: &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Noto Color Emoji&quot;, &quot;Android Emoji&quot;, &quot;EmojiOne Mozilla&quot;, &quot;Twemoji Mozilla&quot;, &quot;Noto Emoji&quot;, &quot;Segoe UI Symbol&quot;, EmojiSymbols, emoji; font-size: 18px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space-collapse: collapse; text-wrap-mode: wrap; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline\">✉\uFE0F</span><span style=\"font-size: 18px\">Email =&nbsp;" + reciverEmail + "</span></li>\n" +
                "          <li style=\"text-align: inherit; font-size: 18px; font-size: 18px\"><span style=\"color: #000000; font-family: &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Noto Color Emoji&quot;, &quot;Android Emoji&quot;, &quot;EmojiOne Mozilla&quot;, &quot;Twemoji Mozilla&quot;, &quot;Noto Emoji&quot;, &quot;Segoe UI Symbol&quot;, EmojiSymbols, emoji; font-size: 18px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space-collapse: collapse; text-wrap-mode: wrap; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline\">\uD83D\uDDDD\uFE0F</span><span style=\"font-size: 18px\">Password =&nbsp;" + password + "</span></li>\n" +
                "      </center>\n" +
                "    </body>\n" +
                "</html>";

    }
}
