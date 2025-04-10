package org.adm.MainApp;


import java.net.URI;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.md.Agency.Agency;
import org.md.Utente.Admin;


public class Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://localhost:7001/";
    private static final String RESOURCE = "org.adm.API.Resource";

    public static void startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE).register(JacksonFeature.class);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args){
        startServer();
        //TODO serve per la prova dopo eliminare

        Agency agency = new Agency.Builder("11111111111")
                .setNome("Pico Pallina")
                .build();

        Admin acquirente = new Admin.Builder(1,"utente1@email.com")
                .setPassword("ciao")
                .setName("Paolo")
                .setCognome("Paoli")
                .setIsSupport(false)
                .setAgency(agency)
                .build();

        System.out.println(acquirente.TranslateToJson());
        System.out.println(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));

    }
}