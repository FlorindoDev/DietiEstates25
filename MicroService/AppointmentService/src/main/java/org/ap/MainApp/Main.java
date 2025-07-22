package org.ap.MainApp;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.md.Appointment.Appointment;
import org.md.Estate.Estate;
import org.md.Utente.Acquirente;
import org.rab.MainSpringBoot.MainSenderNotify;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;


import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;


public class   Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://0.0.0.0:7006/";
    private static final String RESOURCE = "org.ap.API.Resource";

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    //Avvio springBoot per poter creare gli oggetti
    public static final ApplicationContext rabbitMQ = SpringApplication.run(MainSenderNotify.class);

    public static void startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE).register(JacksonFeature.class);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args){

        startServer();
        if (logger.isLoggable(Level.INFO))
            logger.info(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));

    }
}