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


public class   Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://0.0.0.0:7006/";
    private static final String RESOURCE = "org.ap.API.Resource";

    //Avvio springBoot per poter creare gli oggetti
    public static final ApplicationContext rabbitMQ = SpringApplication.run(MainSenderNotify.class);

    public static void startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE).register(JacksonFeature.class);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args){
        startServer();
        //TODO serve per la prova dopo eliminare
        Acquirente acquirente = new Acquirente.Builder(0,"utente1@email.com").build();
        Estate estate = new Estate.Builder(3).build();

        Appointment appointment= new Appointment.Builder(0)
                .setAcquirente(acquirente)
                .setData("2025-11-11")
                .setEstate(estate)
                .build();

        System.out.println(appointment.TranslateToJson());
        System.out.println(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));
    }
}