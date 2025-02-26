package org.ads.MainApp;

import org.ads.MainApp.Interface.AdsEstateService;
import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;

import org.md.Estate.Estate;

import java.util.ArrayList;

//TODO IMPORTARE MQ

public class AdsEstate implements AdsEstateService {

    private EstateDAO adEstate;

    //TODO RIFERIMENTO A MQ

    @Override
    public String createEstate(Estate estate) {

        EstateDAO dao = new EstatePostgreDAO();
        try {
            dao.createEstate(estate);
        }catch (DietiEstateException e) {
            return e.getMessage();
        }

        return "{\"code\": 0, \"message\": \"success of create action (ManagementAccountService)\"}";
    }

    @Override
    public String changeEstate(Estate estate) {

        //TODO PUSH TO RBMQ QUEUE

        EstateDAO dao = new EstatePostgreDAO();
        try {
            dao.changeEstate(estate);
        }catch (DietiEstateException e) {
            return e.getMessage();
        }

        return "{\"code\": 0, \"message\": \"success of update estate action (ManagementAccountService)\"}";

    }

    @Override
    public String loadEstate(Agency agency) {
        AgencyPostgreDAO dao = new AgencyPostgreDAO();

        try {
            ArrayList<Estate> estates = dao.getEstates(agency);
            return convertToJson(estates);
        }catch (DietiEstateException e){
            return e.getMessage();
        }

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