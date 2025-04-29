package org.ac.MainApp.interfacce;

import org.exc.DietiEstateException;
import org.md.Utente.Utente;

public interface LoginService {
    String makeLogin(Utente user);

    String handleGoogleLogIn(String idTokenString) throws DietiEstateException;

    void close();

}
