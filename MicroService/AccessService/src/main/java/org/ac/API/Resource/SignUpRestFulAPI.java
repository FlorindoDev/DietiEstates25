package org.ac.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.ac.API.Interfacce.SignUpAPI;
import org.ac.MainApp.SignUp;
import org.ac.MainApp.interfacce.SignUpService;
import org.md.Utente.Acquirente;

@Path("makeSignup")
public class SignUpRestFulAPI implements SignUpAPI {

    SignUpService singup_access =  new SignUp();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String makeSignUp(Acquirente user) {
        return singup_access.makeSignUp(user);
    }
}
