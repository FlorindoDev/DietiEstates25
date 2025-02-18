
import java.util.*;

/**
 * 
 */
public interface CommunicationWhitDataBase {

    /**
     * @param String 
     * @return
     */
    public Boolean makeQuery(void String);

    /**
     * @return
     */
    public Boolean hasNextRow();

    /**
     * @return
     */
    public Boolean getNextRow();

    /**
     * @param String 
     * @return
     */
    public int extractInt(void String);

    /**
     * @param String 
     * @return
     */
    public String extractString(void String);

    /**
     * @param String 
     * @return
     */
    public Boolean extractBoolean(void String);

}