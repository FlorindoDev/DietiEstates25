
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
    public Boolean validateAgencyName(void String name);

    /**
     * @param String partitaIVA 
     * @return
     */
    public Boolean validatePartitaIVA(void String partitaIVA);

    /**
     * @param String sede 
     * @return
     */
    public Boolean validateSede(void String sede);

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

    /**
     * @param Date start 
     * @param Date end 
     * @return
     */
    public Boolean validateDate(void Date start, void Date end);

}