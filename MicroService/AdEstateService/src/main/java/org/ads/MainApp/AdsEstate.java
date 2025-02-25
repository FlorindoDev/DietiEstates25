package org.ads.MainApp;

import org.ads.MainApp.Interface.AdsEstateService;
import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.AgencyPostgreDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Appointment.Appointment;
import org.md.Estate.Estate;

import java.util.ArrayList;
// IMPORTARE MQ

public class AdsEstate implements AdsEstateService {

    private EstateDAO ad_estate;

    // RIFERIMENTO A MQ

    @Override
    public String createEsteate(Estate estate) {

//        EstateDAO dao = new EstatePostgreDAO();
//        dao.getEstate(null);
        return "";
    }

    @Override
    public String changeEstate(Estate estate) {
        return "";
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
