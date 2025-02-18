
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
     * @return
     */
    public Boolean validateName(void String name);

    /**
     * @param String surname 
     * @return
     */
    public Boolean validateSurname(void String surname);

}