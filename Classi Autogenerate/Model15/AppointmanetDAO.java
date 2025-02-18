
import java.util.*;

/**
 * 
 */
public interface AppointmanetDAO {

    /**
     * @param Utente utente 
     * @return
     */
    public ArrayList<Appointment> getAppointment(void Utente utente);

    /**
     * @param Appointment appointment 
     * @return
     */
    public Void changeStatusAppointment(void Appointment appointment);

    /**
     * @param Utente utente 
     * @param Estate estate 
     * @return
     */
    public Void createAppointment(void Utente utente, void Estate estate);

}