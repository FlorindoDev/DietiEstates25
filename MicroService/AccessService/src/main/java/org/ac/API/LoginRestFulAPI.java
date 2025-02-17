package org.ac.API;

import jakarta.ws.rs.core.MediaType;
import org.ac.API.Interfacce.LoginAPI;
import jakarta.ws.rs.*;
import org.md.Utente.Utente;

@Path("makeLogin")
public class LoginRestFulAPI implements LoginAPI {

    //LoginService login_access = new LoginService();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String makeLogin(Utente utente) {
        return "";
    }
}