package org.sch.MainApp;


import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.net.URI;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://localhost:7012/";
    private static final String RESOURCE = "org.sch.API.Resource";

    public static void startServer() {

        final ResourceConfig resourceConfig = new ResourceConfig().packages(RESOURCE).register(JacksonFeature.class);

        resourceConfig.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
    }


    public static void main(String[] args) {
        startServer();
        //TODO serve per la prova dopo eliminare
        System.out.println(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));
    }
}