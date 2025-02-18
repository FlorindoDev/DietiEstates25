
import java.util.*;

/**
 * 
 */
public interface UtenteDAO {

    /**
     * @param Utente utente 
     * @return
     */
    public Utente getUser(void Utente utente);

    /**
     * @param Utente user 
     * @return
     */
    public Void createUser(void Utente user);

    /**
     * @param Utente changes
     */
    public void updateUser(void Utente changes);

    /**
     * @param Notify Changes 
     * @return
     */
    public Void updateNotify(void Notify Changes);

}