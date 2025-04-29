package org.ac.API.Interfacce;

import jakarta.ws.rs.core.Response;
import org.md.Utente.Utente;

import java.util.Map;

public interface LoginAPI {

    String makeLogin(Utente user);

    Response makeLoginGoogle(Map<String, String> body);

}
