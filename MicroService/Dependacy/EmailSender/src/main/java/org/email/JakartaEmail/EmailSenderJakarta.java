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
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class EmailSenderJakarta implements EmailSender {

    protected String email;

    protected String username;
    protected String passwordEmail;
    protected Properties props;
    protected Session session;

    protected static final Logger logger = Logger.getLogger(EmailSenderJakarta.class.getName());

    public EmailSenderJakarta() {

        // Indirizzo email del mittente

        readCredentials();
        props = new Properties();
        props.put("mail.smtp.host", "smtp.sendgrid.net");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "false");
        props.put("mail.smtp.starttls.enable", "true");

        // Creazione della sessione con autenticazione
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, passwordEmail);
            }
        });


    }

    @Override
    public void sendEmail(String reciverEmail) {
        // è vuoto perche saranno i figli ad inviare email
    }

    private void readCredentials(){
        JsonNode rootNode = null;
        try {

            InputStream inputStream = EmailSenderJakarta.class.getResourceAsStream("/credenzialiEmail.json");
            String jsonString = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8).useDelimiter("\\A").next();
            rootNode = new ObjectMapper().readTree(jsonString);

        } catch (IOException e) {
            logger.severe("[-] Errore lettura credenziali Email");
            System.exit(-1);
        }

        this.email = rootNode.path("email").asText();
        this.passwordEmail = rootNode.path("password").asText();
        this.username = rootNode.path("username").asText();

        logger.info("[!] Username,passowrd e email: " + email + " " + passwordEmail + " " + username);

    }
}
