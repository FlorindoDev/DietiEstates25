package org.not.MainApp;


import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.rab.MainSpringBoot.MainReceiverNotify;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.net.URI;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://localhost:7008/";
    private static final String RESOURCE = "org.not.API.Resource";

    //avvio app che si occuperda di inviare le notifiche
    public static final ApplicationContext rabbitMQ = SpringApplication.run(MainReceiverNotify.class);

    public static void startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE).register(JacksonFeature.class);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args){
        startServer();

        System.out.println(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));

    }
}