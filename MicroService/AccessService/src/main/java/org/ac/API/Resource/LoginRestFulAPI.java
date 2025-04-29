package org.ac.API.Resource;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ac.API.Interfacce.LoginAPI;
import jakarta.ws.rs.*;

import org.ac.MainApp.Login;
import org.ac.MainApp.interfacce.LoginService;

import org.exc.DietiEstateException;
import org.md.Utente.Utente;

import java.util.Map;


@Path("login")
public class LoginRestFulAPI implements LoginAPI {

    private final LoginService login_access =  new Login();
    @POST
    @Path("makeLogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String makeLogin(Utente user) {

        String result = login_access.makeLogin(user);
        login_access.close();

        return result;
    }

    @Override
    @POST
    @Path("google")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeLoginGoogle(Map<String, String> body) {


        if (body == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String idToken = body.get("idToken");

        if (idToken == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\": \"idToken is null\"}").build();
        }

        try {
            String  payload = login_access.handleGoogleLogIn(idToken);

            return Response.status(Response.Status.OK).entity(payload).build();
        } catch (DietiEstateException e) {

            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }
    }
}