package org.not.MainApp;

import org.dao.Interfacce.NotifyDAO;
import org.dao.postgre.NotifyPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;
import org.not.MainApp.Interfacce.NotifyService;

import java.util.ArrayList;
import java.util.List;

public class Notify implements NotifyService {

    private NotifyDAO notifyDAO = new NotifyPostgreDAO();

    @Override
    public String getNotifyAcquirente(Acquirente acquirente) {


        try {
            ArrayList<org.md.Notify.Notify> notifes = notifyDAO.getAllNotifyAcquirente(acquirente);

            return convertListNotifyToJson(notifes);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    @Override
    public String getNotifyAgent(Agent agent) {
        return null;
    }


    private String convertListNotifyToJson(List<org.md.Notify.Notify> notifies) {
        String json = "{\"code\": 0, \"message\": \"success of action get notify\", \"Notify\": [";

        for(org.md.Notify.Notify notify : notifies){
            json = json.concat(notify.TranslateToJson());
            if(!notify.equals(notifies.getLast()))
                json = json.concat(",");

        }

        return json + "]}";
    }

}
