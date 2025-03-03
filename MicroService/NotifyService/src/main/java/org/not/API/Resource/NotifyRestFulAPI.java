package org.not.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.not.API.Interfacce.NotifyAPI;

@Path("Notify")
public class NotifyRestFulAPI implements NotifyAPI {


    @Override
    @Path("updateIdNotifyPush")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateIdNotifyPush() {
        return null;
    }
}
