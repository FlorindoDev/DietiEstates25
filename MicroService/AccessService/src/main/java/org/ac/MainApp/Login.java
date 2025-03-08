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

    private final UtenteDAO utenteDAO;

    public Login(){
        utenteDAO = new UtentePostgreDAO();
    }

    public String makeLogin(Utente user){


        try {
            Utente userFromDB = utenteDAO.getUser(user);
            String emailFromDB = userFromDB.getEmail();



            String role = userFromDB.getClass().getSimpleName();
            return "{\"code\":0 , \"message\":\"" + JWTUtil.generateToken(emailFromDB, role) + "\"}";


        } catch (DietiEstateException e) {
            return e.getMessage();
        }


    }

    public void close(){
        utenteDAO.close();
    }

}
