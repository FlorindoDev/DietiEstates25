package org.not.MainApp;

import org.dao.Interfacce.Factory.QueryParametersNotify;
import org.dao.Interfacce.NotifyDAO;
import org.dao.postgre.Factory.FactoryFilteredQueryNotifyPostgres;
import org.dao.postgre.NotifyPostgreDAO;
import org.exc.DietiEstateException;
import org.md.Utente.Agent;
import org.not.MainApp.Interfacce.NotifyService;

import java.util.List;

public class Notify implements NotifyService {

    private final NotifyDAO notifyDAO = new NotifyPostgreDAO();

    @Override
    public String getNotifyAcquirente(QueryParametersNotify parameters) {

        FactoryFilteredQueryNotifyPostgres queryFacotry = new FactoryFilteredQueryNotifyPostgres();

        try {

            String query = queryFacotry.notifySelectQueryAllColumns(parameters);

            List<org.md.Notify.Notify> notifes = notifyDAO.getNotifyAcquirenteAllFilter(query, parameters);

            return convertListNotifyToJson(notifes);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }

    }

    @Override
    public String getNotifyAgent(QueryParametersNotify parameters) {

        FactoryFilteredQueryNotifyPostgres queryFacotry = new FactoryFilteredQueryNotifyPostgres();

        try {

            String query = queryFacotry.agentNotifySelectQueryAllFilter(parameters);
            System.out.println(query);

            List<org.md.Notify.Notify> notifes = notifyDAO.getAgentNotifyAcquirenteAllFilter(query, parameters);

            return convertListNotifyToJson(notifes);

        } catch (DietiEstateException e) {
            return e.getMessage();
        }
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

    @Override
    public void close() {
        notifyDAO.close();
    }
}
