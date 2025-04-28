package org.ac.API.Resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ac.API.Interfacce.SignUpAPI;
import org.ac.MainApp.SignUp;
import org.ac.MainApp.interfacce.SignUpService;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

import java.util.Map;

@Path("signup")
public class SignUpRestFulAPI implements SignUpAPI {

    private final SignUpService singup_access =  new SignUp();

    @Path("makeSignup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public String makeSignUp(Acquirente user) {
        String result = singup_access.makeSignUp(user);
        singup_access.close();
        return result;
    }


    @Override
    @POST
    @Path("google")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeSignUpGoogle(Map<String, String> body) {


        if (body == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String idToken = body.get("idToken");

        if (idToken == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"idToken is null\"}").build();
        }

        try {
            String  payload = singup_access.handleGoogleSignUp(idToken);

            return Response.status(Response.Status.OK).entity(payload).build();
        } catch (DietiEstateException e) {

            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
