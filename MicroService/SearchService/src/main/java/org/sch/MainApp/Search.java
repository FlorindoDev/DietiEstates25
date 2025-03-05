package org.sch.MainApp;

import org.sch.MainApp.Interfacce.SearchService;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Search implements SearchService {
    @Override
    public String allCity() {

        try{
            InputStream inputStream;
            inputStream = Search.class.getResourceAsStream("/city_IT.json");
            String jsonString = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            return jsonString;
        }catch (Exception e){
            return "{\"code\": 110, \"message\": \"error to read json\"}";
        }



    }


}
