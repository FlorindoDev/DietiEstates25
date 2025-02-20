package org.ac.MainApp;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;
import org.md.Utente.Utente;

import java.io.IOException;
import java.net.URI;


public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://192.168.0.216:7001/";
    public static final String RESOURCE = "org.ac.API.Resource";

    public static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        //TODO serve per la prova dopo eliminare
        Utente acquirente = new Acquirente.Builder(1,"utente1@email.com")
                .setPassword("ciao")
                .build();
        System.out.println(acquirente.Translate());
        System.out.println(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}