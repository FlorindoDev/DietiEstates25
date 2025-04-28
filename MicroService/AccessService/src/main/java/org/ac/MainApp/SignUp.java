package org.ac.MainApp;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import org.ac.MainApp.interfacce.SignUpService;
import org.exc.DietiEstateMicroServiceException.DietiEstateMicroSercviceException;

import org.va.Validate;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.postgre.AcquirentePostgreDAO;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;
import org.va.Validator;


import java.security.SecureRandom;
import java.util.Collections;

public class SignUp implements SignUpService {

    private final AcquirenteDAO signup;

    private static final String CHARACTERS_FOR_GENERATE_PASSWORD = "Z5v!EeR9aFGdySO$fcgDu4Wpi8xVo2N1tXClAnsbz6BTrYQwLm_3IjPHKkqhM0UJ7";

    private final SecureRandom random = new SecureRandom();

    public SignUp() {
        signup = new AcquirentePostgreDAO();
    }

    @Override
    public String makeSignUp(Acquirente user) {

        Validator validaitor = Validate.getInstance();

        try{
            if(signup.isUserAbsentOverAll(user)){
                validaitor.validateEmail(user.getEmail());
                validaitor.validatePassword(user.getPassword());
                validaitor.validateGenerality(user.getNome(), user.getCognome());
                signup.createUser(user);
                //TODO FARE un oggetto per messaggio di buon fine
                return "{\"code\": 0, \"message\": \"success of action SignUp\"}";
            }
        }catch(DietiEstateException e){
            return e.getMessage();
        }

        return "";

    }

    public String handleGoogleSignUp(String idTokenString) throws DietiEstateException {
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
                //String userId = payload.getSubject();        // ID univoco Google
                //String pictureUrl = (String) payload.get("picture");

                String email = payload.getEmail();           // email utente
                String name = (String) payload.get("name");  // nome completo
                String cognome = (String) payload.get("family_name");  // nome completo


                Acquirente acquirente = new Acquirente.Builder(0,email)
                        .setName(name)
                        .setCognome(cognome)
                        .setPassword(generateRandomWord(24))
                        .build();

                return makeSignUp(acquirente);

            } else {
                throw new RuntimeException();
            }

        } catch (Exception e) {
            throw new DietiEstateMicroSercviceException(e.getMessage());
        }

    }

    private String generateRandomWord(int length) {

        String characters = CHARACTERS_FOR_GENERATE_PASSWORD;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Override
    public void close() {
        signup.close();
    }
}
