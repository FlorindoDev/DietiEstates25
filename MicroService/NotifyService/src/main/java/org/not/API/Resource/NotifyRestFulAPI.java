package org.not.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;
import org.not.API.Interfacce.NotifyAPI;
import org.not.MainApp.Interfacce.NotifyService;
import org.not.MainApp.Notify;


@Path("/notify")
public class NotifyRestFulAPI implements NotifyAPI {

    NotifyService notify = new Notify();

    @Override
    @Path("/getNotifyAcquirente")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getNotifyAcquirente(Acquirente acquirente) {


        return notify.getNotifyAcquirente(acquirente);
    }

    @Override
    @Path("/getNotifyAgent")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getNotifyAgent(Agent agent) {

        return null;
    }
}
