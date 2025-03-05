package org.sch.MainApp;

import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.exc.DietiEstateException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import org.sch.MainApp.Interfacce.SearchService;



import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Search implements SearchService {

    private EstateDAO estateDAO = new EstatePostgreDAO();

    @Override
    public String allCity() {

        try{
            InputStream inputStream;
            inputStream = Search.class.getResourceAsStream("/city_IT.json");
            return new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        }catch (Exception e){
            return "{\"code\": 110, \"message\": \"error to read json\"}";
        }



    }

    @Override
    public String suggestionCities(Indirizzo indirizzo) {

        try{
            InputStream inputStream;
            inputStream = Search.class.getResourceAsStream("/city_IT.json");
            String json = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            JSONArray cities = new JSONArray(json);

            List<JSONObject> filteredCities = cities.toList().stream()
                .map(obj -> new JSONObject((java.util.Map<?, ?>) obj))
                .filter(city -> city.getString("name").toLowerCase().contains(indirizzo.getCitta().toLowerCase()))
                .collect(Collectors.toList());

            JSONArray resultArray = new JSONArray(filteredCities);
            return resultArray.toString(4);
        }catch (Exception e){
            return "{\"code\": 110, \"message\": \"error to read json\"}";
        }
    }

    @Override
    public String estatesSerachFromCity(Indirizzo indirizzo) {
        List<Estate> estates;
        try {
            estates = estateDAO.estatesSerachFromCity(indirizzo);
        } catch (DietiEstateException e) {
            return "{\"code\": 10, \"message\": \"There aren't estets in this city\"}";
        }

        String json = "{\"code\": 0, \"message\": \"success of action get estate\", \"Estates\": [";

        for (Estate estate : estates){
            json = json.concat(estate.TranslateToJson());
            if(!estate.equals(estates.getLast()))
                json = json.concat(",");
        }


        json = json + "]}";

        return json;
    }


}
