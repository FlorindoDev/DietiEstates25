package org.ac.MainApp;

import org.ac.MainApp.interfacce.SignUpService;
import org.ac.Validaitor.Validate;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.postgre.AcquirentePostgreDAO;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;

public class SignUp implements SignUpService {

    AcquirenteDAO singup = new AcquirentePostgreDAO();
    public SignUp() {
    }

    @Override
    public String makeSignUp(Acquirente user) {

        Validate validaitor = Validate.getInstance();

        try{

            validaitor.validateEmail(user.getEmail());
            validaitor.validatePassword(user.getPassword());
            validaitor.validateGenerality(user.getNome(), user.getCognome());
            singup.createUser(user);
            //TODO FARE un oggetto per messaggio di buon fine
            return "{\"code\": 0, \"message\": \"success of action SignUp\"}";

        }catch(DietiEstateException e){
            return e.getMessage();
        }




    }
}
