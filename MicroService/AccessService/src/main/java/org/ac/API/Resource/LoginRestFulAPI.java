package org.ac.API.Resource;

import jakarta.ws.rs.core.MediaType;
import org.ac.API.Interfacce.LoginAPI;
import jakarta.ws.rs.*;

import org.ac.API.JWT.JWTUtil;
import org.ac.MainApp.Login;
import org.ac.MainApp.interfacce.LoginService;

import org.md.Utente.Agent;
import org.md.Utente.Utente;

import java.util.ArrayList;
import java.util.List;

@Path("makeLogin")
public class LoginRestFulAPI implements LoginAPI {

    LoginService login_access =  new Login();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String makeLogin(Utente user) {

        String token = login_access.makeLogin(user);

        return token;
    }
}