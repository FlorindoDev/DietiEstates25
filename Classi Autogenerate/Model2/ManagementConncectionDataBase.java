
import java.util.*;

/**
 * 
 */
public interface ManagementConncectionDataBase {

    /**
     * @return
     */
    public Void createConnection();

    /**
     * @return
     */
    public Viod closeConnection();

    /**
     * @return
     */
    public Void readDataBaseAccessFile();

}