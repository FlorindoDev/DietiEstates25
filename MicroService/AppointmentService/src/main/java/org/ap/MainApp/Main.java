package org.ap.MainApp;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.md.Appointment.Appointment;
import org.md.Estate.Estate;
import org.md.Utente.Acquirente;

import java.io.IOException;
import java.net.URI;


public class   Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:7001/";
    public static final String RESOURCE = "org.ap.API.Resource";

    public static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE).register(JacksonFeature.class);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
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
        System.in.read();
        server.shutdownNow();
    }
}