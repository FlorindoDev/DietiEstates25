package org.not.API.Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.not.API.Interfacce.NotifyAPI;
import org.not.MainApp.Interfacce.NotifyService;
import org.not.MainApp.Notify;


@Path("/api")
public class NotifyRestFulAPI implements NotifyAPI {

    NotifyService notify = new Notify();

    @Override
    @Path("/notifies/acquirente")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getNotifyAcquirente(@BeanParam NotifyQuery query) {

        String email = query.getEmail();


        if(email == null || email.isEmpty()) {
            return "{\"code\":-1, \"error\": \"email parameter is required\"}";
        }

        String result = notify.getNotifyAcquirente(query);


        notify.close();
        return result;
    }

    @Override
    @Path("/notifies/agent")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getNotifyAgent(@BeanParam NotifyQuery query) {

        String email = query.getEmail();


        if(email == null || email.isEmpty()) {
            return "{\"code\":-1, \"error\": \"email parameter is required\"}";
        }

        String result = notify.getNotifyAgent(query);


        notify.close();
        return result;
    }
}



