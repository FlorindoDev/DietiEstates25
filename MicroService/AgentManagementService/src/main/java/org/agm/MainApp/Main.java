package org.agm.MainApp;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.md.Utente.Agent;


public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:7001/";
    public static final String RESOURCE = "org.agm.API.Resource";

    public static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        //TODO serve per la prova dopo eliminare

        Agency agency = new Agency.Builder("11111111111")
                .setEmail("pincopallina1@gmail.com")
                .setNome("Pico Pallina")
                .build();

        Agent acquirente = new Agent.Builder(1,"utente1@email.com")
                .setPassword("ciao")
                .setName("Paolo")
                .setCognome("Paoli")
                .setAgency(agency)
                .setBiografia("Sono un figo")
                .setProfilePic("foto.png")
                .setIdPushNotify("TOKEN")
                .setNotifyAppointment(true)
                .build();

        System.out.println(acquirente.Translate());

        System.out.println(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}