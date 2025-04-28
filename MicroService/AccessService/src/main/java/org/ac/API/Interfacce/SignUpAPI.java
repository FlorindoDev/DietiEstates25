package org.ac.API.Interfacce;

import jakarta.ws.rs.core.Response;
import org.md.Utente.Acquirente;

import java.util.Map;

public interface SignUpAPI {

    String makeSignUp(Acquirente utente);

    Response makeSignUpGoogle(Map<String, String> body);
}
