package org.ac.MainApp;

import org.ac.MainApp.interfacce.SignUpService;
import org.va.Validate;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.postgre.AcquirentePostgreDAO;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;
import org.va.Validator;

public class SignUp implements SignUpService {

    private final AcquirenteDAO signup;
    public SignUp() {
        signup = new AcquirentePostgreDAO();
    }

    @Override
    public String makeSignUp(Acquirente user) {

        Validator validaitor = Validate.getInstance();

        try{
            if(signup.isUserAbsentOverAll(user)){
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
