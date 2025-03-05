package org.sch.MainApp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.md.Geolocalizzazione.Indirizzo;
import org.sch.MainApp.Interfacce.SearchService;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Search implements SearchService {
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


}
