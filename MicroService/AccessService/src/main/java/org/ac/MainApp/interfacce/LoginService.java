package org.ac.MainApp.interfacce;

import org.md.Utente.Utente;

public interface LoginService {
    String makeLogin(Utente user);

    void close();

}
