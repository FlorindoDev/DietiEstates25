package org.ac.MainApp;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.ac.API.JWT.JWTUtil;
import org.ac.MainApp.interfacce.LoginService;
import org.dao.Interfacce.UtenteDAO;

import org.dao.postgre.UtentePostgreDAO;
import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.ErrorValidateTokenGoogle;
import org.exc.DietiEstateMicroServiceException.TokenIsNull;
import org.md.Utente.Utente;

import java.util.Collections;


public class Login implements LoginService{

    private final UtenteDAO utenteDAO;

    public Login(){
        utenteDAO = new UtentePostgreDAO();
    }

    public String makeLogin(Utente user){


        try {
            Utente userFromDB = utenteDAO.getUser(user);
            String emailFromDB = userFromDB.getEmail();



            String role = userFromDB.getClass().getSimpleName();
            return "{\"code\":0 , \"message\":\"" + JWTUtil.generateToken(emailFromDB, role) + "\"}";


        } catch (DietiEstateException e) {
            return e.getMessage();
        }


    }

    public String handleGoogleLogIn(String idTokenString) throws DietiEstateException {
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = null;
        try {
            verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    jsonFactory)
                    .setAudience(Collections.singletonList(System.getenv("CLIENT_ID_GOOGLE")))
                    .build();


            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();           // email utente


                Utente user = utenteDAO.getUserFromEmail(email);

                String role = user.getClass().getSimpleName();
                return "{\"code\":0 , \"message\":\"" + JWTUtil.generateToken(email, role) + "\"}";

            } else {
                throw new TokenIsNull();
            }

        } catch (Exception e) {
            throw new ErrorValidateTokenGoogle(e.getMessage());
        }

    }

    public void close(){
        utenteDAO.close();
    }

}
