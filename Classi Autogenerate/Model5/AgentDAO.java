
import java.util.*;

/**
 * 
 */
public interface AgentDAO {

    /**
     * @param Agent agent 
     * @return
     */
    public Void createAgent(void Agent agent);

    /**
     * @param Agent agent 
     * @return
     */
    public Void deleteAgent(void Agent agent);

    /**
     * @param Agent agent 
     * @param Estate estate 
     * @return
     */
    public Void changeAgentEstate(void Agent agent, void Estate estate);

    /**
     * @param Agency agency 
     * @return
     */
    public ArrayList<Agent> getAgents(void Agency agency);

}