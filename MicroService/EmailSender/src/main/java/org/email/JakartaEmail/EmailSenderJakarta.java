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
import java.security.Key;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class EmailSenderJakarta implements EmailSender {

    protected String email;
    protected String password_email;

    protected Properties props;

    protected Session session;

    protected static final Logger logger = Logger.getLogger(EmailSenderJakarta.class.getName());

    //TODO AGGIUSTARE EMAIL SENDER
    public EmailSenderJakarta() {

        // Indirizzo email del mittente

        readCredentials();
        this.props = new Properties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Creazione della sessione con autenticazione
        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password_email);
            }
        });

    }

    @Override
    public void SendEmail(String reciver_email) {}

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
        this.password_email = rootNode.path("password").asText();
        logger.info("[!] Username e passowrd email " + email + " " + password_email);

    }
}
