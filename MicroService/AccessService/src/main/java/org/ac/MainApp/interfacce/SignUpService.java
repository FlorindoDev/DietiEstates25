package org.ac.MainApp.interfacce;

import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

public interface SignUpService {
    String makeSignUp(Acquirente user);


    String handleGoogleSignUp(String idTokenString) throws DietiEstateException;

    void close();

}
