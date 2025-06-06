package org.ads.MainApp;

import java.util.ArrayList;

import org.ads.MainApp.Interface.AdsEstateService;
import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Estate.Estate;
import org.rab.Interfacce.ManagementSenderNotifyMQ;
import org.rab.Resource.Senders.ManagementSenderNotifyRabbitMQ;
import org.springframework.context.ApplicationContext;

public class AdsEstate implements AdsEstateService {

    AgencyPostgreDAO agencyDao;
    EstateDAO estateDao;

    ManagementSenderNotifyMQ senderMQ;

    public AdsEstate (ApplicationContext rabbitMQ){
        senderMQ = rabbitMQ.getBean(ManagementSenderNotifyRabbitMQ.class);
    }

    @Override
    public String createEstate(Estate estate) {

        try {
            estateDao = new EstatePostgreDAO();
            estateDao.createEstate(estate);
            senderMQ.enQueueEstateNotify("{}");
        }catch (DietiEstateException e) {
            return e.getMessage();
        }

        return "{\"code\": 0, \"message\": \"success of create action (ManagementAccountService)\"}";
    }

    @Override
    public String changeEstate(Estate estate) {

        //TODO PUSH TO RBMQ QUEUE

        try {
            estateDao = new EstatePostgreDAO();
            estateDao.updateEstate(estate);
        }catch (DietiEstateException e) {
            return e.getMessage();
        }

        return "{\"code\": 0, \"message\": \"success of update estate action (ManagementAccountService)\"}";

    }

    @Override
    public String loadEstate(Agency agency) {

        try {
            agencyDao = new AgencyPostgreDAO();
            ArrayList<Estate> estates = agencyDao.getEstates(agency);
            return convertToJson(estates);
        }catch (DietiEstateException e){
            return e.getMessage();
        }

    }

    @Override
    public void close() {
        if (agencyDao!=null) {agencyDao.close();}
        if (estateDao!=null) {estateDao.close();}
    }


    private String convertToJson(ArrayList<Estate> estates) {

        String json = "{\"code\": 0, \"message\": \"success of action get estates\", \"AdsService\": [";

        for(Estate estate : estates){
            json = json.concat(estate.TranslateToJson());

            if(!estate.equals(estates.getLast()))
                json = json.concat(",");

        }

        return json + "]}";
    }

}