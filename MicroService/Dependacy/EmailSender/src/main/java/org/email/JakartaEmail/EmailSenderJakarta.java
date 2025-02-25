package org.email.JakartaEmail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.email.Interfacce.EmailSender;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class EmailSenderJakarta implements EmailSender {

    protected String email;
    protected String passwordEmail;

    protected Properties props;

    protected Session session;

    protected static final Logger logger = Logger.getLogger(EmailSenderJakarta.class.getName());

    //TODO AGGIUSTARE EMAIL SENDER
    public EmailSenderJakarta() {

        // Indirizzo email del mittente

        readCredentials();
        props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.ssl.checkserveridentity", "true");
        props.put("mail.smtp.socketFactory.fallback", "false");                     // Porta TLS

        // Creazione della sessione con autenticazione
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, passwordEmail);
            }
        });

        session.setDebug(true);

    }

    @Override
    public void sendEmail(String reciverEmail) { // è vuoto perche saranno i figli ad inviare email
    }

    private void readCredentials(){
        JsonNode rootNode = null;
        try {

            InputStream inputStream = EmailSenderJakarta.class.getResourceAsStream("/credenzialiEmail.json");
            String jsonString = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            rootNode = new ObjectMapper().readTree(jsonString);

        } catch (IOException e) {
            logger.severe("[-] Errore lettura credenziali Email");
            System.exit(-1);
        }

        this.email = rootNode.path("email").asText();
        this.passwordEmail = rootNode.path("password").asText();
        logger.info("[!] Username e passowrd email " + email + " " + passwordEmail);

    }
}
