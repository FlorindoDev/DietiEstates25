package org.ac.MainApp;

import org.ac.API.JWT.JWTUtil;
import org.ac.MainApp.interfacce.LoginService;
import org.dao.Interfacce.UtenteDAO;
import org.dao.postgre.UtentePostgreDAO;
import org.md.Utente.Utente;

import java.util.ArrayList;
import java.util.List;


public class Login implements LoginService{

    private UtenteDAO utenteDAO = new UtentePostgreDAO();

    public Login(){}

    public String makeLogin(Utente user){

        List<String> roles = new ArrayList<>();
        roles.add(user.getClass().getSimpleName());

        return JWTUtil.generateToken(user.getNome(),roles);
    }
}
