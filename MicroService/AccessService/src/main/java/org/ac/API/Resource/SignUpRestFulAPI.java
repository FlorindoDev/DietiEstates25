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

@Path("signup")
public class SignUpRestFulAPI implements SignUpAPI {

    private final SignUpService singup_access =  new SignUp();

    @Path("makeSignup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Override
    public String makeSignUp(Acquirente user) {
        String result = singup_access.makeSignUp(user);
        singup_access.close();
        return result;
    }
}
