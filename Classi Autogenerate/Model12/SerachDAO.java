
import java.util.*;

/**
 * 
 */
public interface SerachDAO {

    /**
     * @param EstateFilter Filter 
     * @param String City 
     * @return
     */
    public ArrayList<Estate> getEstatesFromCity(void EstateFilter Filter, void String City);

    /**
     * @param EstateFilter Filter 
     * @param Circumference circumference 
     * @return
     */
    public ArrayList<Estate> getEstatesFromRadius(void EstateFilter Filter, void Circumference circumference);

    /**
     * @param Estate estate 
     * @return
     */
    public String coordinatesEstates(void Estate estate);

}