package org.ag.MainApp;


import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.md.Agency.Agency;


import java.net.URI;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:7002/";
    public static final String RESOURCE = "org.ag.API.Resource";


    public static void startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE).register(JacksonFeature.class);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args){
        startServer();

        Agency agency = new Agency.Builder("55").setNome("marco").build();
        System.out.println(agency.TranslateToJson());
        System.out.println(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));
    }
}