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

    ManagementAccount managementAccount;

    @Override
    @POST
    @Path("applyChangeAcquirente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String applyChangeAcquirente(Acquirente utente) {
        managementAccount = new ManagementAccount();
        String response = managementAccount.applyChangeAcquirente(utente);
        managementAccount.close();
        return response;
    }

    @Override
    @POST
    @Path("applyChangeAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String applyChangeAgent(Agent utente) {
        managementAccount = new ManagementAccount();
        String response = managementAccount.applyChangeAgent(utente);
        managementAccount.close();
        return response;
    }

    @Override
    @POST
    @Path("applyChangeAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String applyChangeAdmin(Admin utente) {
        managementAccount = new ManagementAccount();
        String response = managementAccount.applyChangeAdmin(utente);
        managementAccount.close();
        return response;
    }

    @Override
    @POST
    @Path("getAccountAcquirente")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAccountAcquirente(Acquirente utente) {
        managementAccount = new ManagementAccount();
        String response = managementAccount.getAccountAcquirente(utente);
        managementAccount.close();
        return response;
    }

    @Override
    @POST
    @Path("getAccountAdmin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAccountAdmin(Admin utente) {
        managementAccount = new ManagementAccount();
        String response = managementAccount.getAccountAdmin(utente);
        managementAccount.close();
        return response;
    }

    @Override
    @POST
    @Path("getAccountAgent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getAccountAgent(Agent utente) {
        managementAccount = new ManagementAccount();
        String response = managementAccount.getAccountAgent(utente);
        managementAccount.close();
        return response;
    }
}
