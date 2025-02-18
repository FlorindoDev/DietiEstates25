
import java.util.*;

/**
 * 
 */
public interface EstateDAO {

    /**
     * @param Estate new 
     * @param Agency agancy 
     * @param Utente user 
     * @return
     */
    public String createEstate(void Estate new, void Agency agancy, void Utente user);

    /**
     * @param Estate changes 
     * @param Agency agancy 
     * @param Utente user 
     * @return
     */
    public String changeEstate(void Estate changes, void Agency agancy, void Utente user);

    /**
     * @param Agency agency 
     * @return
     */
    public ArrayList<Estate> getEstates(void Agency agency);

}