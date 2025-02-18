
import java.util.*;

/**
 * 
 */
public interface Validator {

    /**
     * @param String email 
     * @return
     */
    public Boolean validateEmail(void String email);

    /**
     * @param String password 
     * @return
     */
    public Boolean validatePassword(void String password);

    /**
     * @param String name 
     * @param String cognome 
     * @return
     */
    public Boolean validateGenerality(void String name, void String cognome);

}