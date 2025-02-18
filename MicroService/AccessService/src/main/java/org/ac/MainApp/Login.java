package org.ac.MainApp;

import org.ac.API.JWT.JWTUtil;
import org.ac.MainApp.interfacce.LoginService;
import org.md.Utente.Utente;

import java.util.ArrayList;
import java.util.List;


public class Login implements LoginService{

    public Login(){}

    public String makeLogin(Utente user){

        List<String> roles = new ArrayList<>();
        roles.add(user.getClass().getSimpleName());
        System.out.println(user.getNome());
        System.out.println(JWTUtil.generateToken(user.getNome(),roles));

        return JWTUtil.generateToken(user.getNome(),roles);
    }
}
