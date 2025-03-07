package org.not.API.Resource;

import jakarta.ws.rs.*;
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
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getNotifyAcquirente(@QueryParam("email") String email, @QueryParam("orderbydate") boolean order) {

        Acquirente acquirente = null;
        String result = null;

        if(email != null)acquirente = new Acquirente.Builder(0,email).build();


        if(order){
            result = notify.getNotifyAcquirente(acquirente, "Order by dataricezione DESC");
        }else{
            result = notify.getNotifyAcquirente(acquirente, " ");
        }

        notify.close();
        return result;
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
