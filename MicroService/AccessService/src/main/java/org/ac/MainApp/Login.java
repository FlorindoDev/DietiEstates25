package org.ac.MainApp;

import org.ac.API.JWT.JWTUtil;
import org.ac.MainApp.interfacce.LoginService;
import org.dao.Interfacce.UtenteDAO;

import org.dao.postgre.UtentePostgreDAO;
import org.exc.DietiEstateException;
import org.md.Utente.Utente;

import java.util.ArrayList;
import java.util.List;


public class Login implements LoginService{

    private UtenteDAO utenteDAO = new UtentePostgreDAO();

    public Login(){}

    public String makeLogin(Utente user){


        try {
            Utente userFromDB = utenteDAO.getUser(user);
            String emailFromDB = userFromDB.getEmail();


            List<String> roles = new ArrayList<>();
            roles.add(userFromDB.getClass().getSimpleName());
            return "{\"code\":0\"message\":\"" + JWTUtil.generateToken(emailFromDB,roles) + "\"}";


        } catch (DietiEstateException e) {
            return e.getMessage();
        }


    }
}
