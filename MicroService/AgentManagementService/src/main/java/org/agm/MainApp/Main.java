package org.agm.MainApp;


import java.net.URI;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.md.Agency.Agency;
import org.md.Estate.ClasseEnergetica.A;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Estate.Mode.Vendita;
import org.md.Estate.Status.New;
import org.md.Utente.Agent;


public class Main {
    // Base URI the Grizzly HTTP server will listen on
    private static final String BASE_URI = "http://localhost:7001/";
    private static final String RESOURCE = "org.agm.API.Resource";

    public static void startServer() {

        final ResourceConfig rc = new ResourceConfig().packages(RESOURCE).register(JacksonFeature.class);
        rc.property(ServerProperties.WADL_FEATURE_DISABLE, true);

        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args){

        //TODO serve per la prova dopo eliminare

        Agency agency = new Agency.Builder("11111111111")
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

        Indirizzo indirizzo = new Indirizzo.Builder<>(0)
                .setStato("Italia")
                .setCap(80125)
                .setVia("Via al mare")
                .setCitta("Napoli")
                .setNumeroCivico("15")
                .build();

        Estate estate = new Estate.Builder<>(0)
                .setAgenziaBuilder(agency)
                .setClasseEnergeticaBuilder(new A())
                .setStatoBuilder(new New())
                .setAgenteBuilder(acquirente)
                .setDescrizioneBuilder("Bellissima")
                .setElevatorBuilder(false)
                .setFloorBuilder(1)
                .setFotoBuilder("foto di casa al mare")
                .setGarageBuilder(3)
                .setModeBuilder(new Vendita())
                .setPriceBuilder(1000000)
                .setRoomsBuilder(10)
                .setSpaceBuilder(300)
                .setWcBuilder(3)
                .setIndirizzoBuilder(indirizzo)
                .build();

        System.out.println(estate.TranslateToJson());

        startServer();

        System.out.println(String.format("Jersey app started with endpoints available at " + "%s%nHit Ctrl-C to stop it...", BASE_URI));
    }
}