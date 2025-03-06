package org.ac.MainApp.interfacce;

import org.md.Utente.Acquirente;

public interface SignUpService {
    String makeSignUp(Acquirente user);

    void close();

}
