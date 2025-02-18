
import java.util.*;

/**
 * 
 */
public interface Appointmanet {

    /**
     * @param Utente utente 
     * @return
     */
    public ArrayList<Appointment> loadAppointment(void Utente utente);

    /**
     * @param Appointment appointment 
     * @return
     */
    public Void acceptAppointment(void Appointment appointment);

    /**
     * @param Appointment appointment 
     * @return
     */
    public Void declineAppointment(void Appointment appointment);

    /**
     * @param Utente utente 
     * @param Estate estate 
     * @return
     */
    public Void makeAppointment(void Utente utente, void Estate estate);

}