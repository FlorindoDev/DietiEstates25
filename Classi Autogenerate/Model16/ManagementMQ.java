
import java.util.*;

/**
 * 
 */
public interface ManagementMQ {

    /**
     * @param String message 
     * @return
     */
    public Void enQueueMQ(void String message);

    /**
     * @return
     */
    public Void createQueueMQ();

    /**
     * @return
     */
    public Void listeningAndDeQueue();

    /**
     * @return
     */
    public Void createConnection();

}