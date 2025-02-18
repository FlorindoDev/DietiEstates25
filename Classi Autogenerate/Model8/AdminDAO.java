
import java.util.*;

/**
 * 
 */
public interface AdminDAO {

    /**
     * @param Admin admin 
     * @return
     */
    public Void createAdmin(void Admin admin);

    /**
     * @param Agency agency 
     * @return
     */
    public ArrayList<Admin> getAdmins(void Agency agency);

    /**
     * @param Admin admin 
     * @return
     */
    public Void updateAdmin(void Admin admin);

    /**
     * @param Admin Admin 
     * @return
     */
    public Void deleteAdmin(void Admin Admin);

}