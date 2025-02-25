package org.ac.API.Resource;

import jakarta.ws.rs.core.MediaType;
import org.ac.API.Interfacce.LoginAPI;
import jakarta.ws.rs.*;

import org.ac.MainApp.Login;
import org.ac.MainApp.interfacce.LoginService;

import org.md.Utente.Utente;


@Path("login")
public class LoginRestFulAPI implements LoginAPI {

    LoginService login_access =  new Login();
    @POST
    @Path("makeLogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String makeLogin(Utente user) {

        return login_access.makeLogin(user);
    }
}