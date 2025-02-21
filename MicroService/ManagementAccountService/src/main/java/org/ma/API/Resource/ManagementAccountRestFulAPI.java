package org.ma.API.Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.ma.API.Interface.ManagementAccountAPI;
import org.md.Utente.Acquirente;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

import org.ma.MainApp.ManagementAccount;

@Path("ManagementAccount")
public class ManagementAccountRestFulAPI implements ManagementAccountAPI {

    ManagementAccount managementAccount = new ManagementAccount();

    @Override
    @POST
    @Path("applyChangeAcquirente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String applyChangeAcquirente(Acquirente utente) {
        return managementAccount.applyChangeAcquirente(utente);
    }

    @Override
    @POST
    @Path("applyChangeAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String applyChangeAgent(Agent utente) {
        return managementAccount.applyChangeAgent(utente);
    }

    @Override
    @POST
    @Path("applyChangeAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String applyChangeAdmin(Admin utente) {
        return managementAccount.applyChangeAdmin(utente);
    }

    @Override
    @POST
    @Path("getAccountAcquirente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getAccountAcquirente(Acquirente utente) {
        return managementAccount.getAccountAcquirente(utente);
    }

    @Override
    @POST
    @Path("getAccountAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getAccountAdmin(Admin utente) {
        return managementAccount.getAccountAdmin(utente);
    }

    @Override
    @POST
    @Path("getAccountAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getAccountAgent(Agent utente) {
        return managementAccount.getAccountAgent(utente);
    }
}
