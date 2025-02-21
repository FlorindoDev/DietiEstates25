package org.ac.MainApp;

import org.ac.MainApp.interfacce.SignUpService;
import org.ac.Validator.Validate;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.postgre.AcquirentePostgreDAO;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

public class SignUp implements SignUpService {

    public SignUp() {
    }

    @Override
    public String makeSignUp(Acquirente user) {

        AcquirenteDAO signup = new AcquirentePostgreDAO();

        Validate validaitor = Validate.getInstance();


        try{
            if(signup.isUserAbsent(user)){
                validaitor.validateEmail(user.getEmail());
                validaitor.validatePassword(user.getPassword());
                validaitor.validateGenerality(user.getNome(), user.getCognome());
                signup.createUser(user);
                //TODO FARE un oggetto per messaggio di buon fine
                return "{\"code\": 0, \"message\": \"success of action SignUp\"}";
            }
        }catch(DietiEstateException e){
            return e.getMessage();
        }

        return "";

    }
}
