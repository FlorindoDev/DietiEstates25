package org.ac.MainApp;

import org.ac.API.JWT.JWTUtil;
import org.ac.MainApp.interfacce.LoginService;
import org.dao.Interfacce.UtenteDAO;
import org.dao.Separators.UserSeparatorsGetUser;
import org.dao.postgre.UtentePostgreDAO;
import org.md.Utente.Utente;
import org.md.Utente.interfacce.UserSeparators;

import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;
import java.util.List;


public class Login implements LoginService{

    private UtenteDAO utenteDAO = new UtentePostgreDAO();

    public Login(){}

    public String makeLogin(Utente user){

        UserSeparatorsGetUser spliter = new UserSeparatorsGetUser(utenteDAO);
        user.Separator(spliter);

        if(spliter.getUser() == null){
            return "{\"code\": 0 , \"error\": \" User name o password sbagliate\"}";
        }else{
            Utente userFromDB = spliter.getUser();
            int id_user = userFromDB.getId_user();
            String emailFromDB = userFromDB.getEmail();
            String passwordFromDB = userFromDB.getPassword();

            if(emailFromDB.equalsIgnoreCase(user.getEmail()) && passwordFromDB.equals(user.getPassword())){

                List<String> roles = new ArrayList<>();
                roles.add(userFromDB.getClass().getSimpleName());
                return JWTUtil.generateToken(emailFromDB,roles);

            }else{
                return "{\"code\": 0 , \"error\": \" User name o password sbagliate\"}";
            }

        }



    }
}
