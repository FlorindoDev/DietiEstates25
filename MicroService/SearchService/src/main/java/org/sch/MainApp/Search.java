package org.sch.MainApp;

import org.dao.Interfacce.CronologiaRicercaDAO;
import org.dao.Interfacce.EstateDAO;
import org.dao.postgre.CronologiaRicercaPostgreDAO;
import org.dao.postgre.EstatePostgreDAO;
import org.exc.DietiEstateException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.md.Estate.Estate;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Estate.EstateFilter;
import org.sch.MainApp.Interfacce.SearchService;



import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Search implements SearchService {

    private EstateDAO estateDAO = new EstatePostgreDAO();
    private CronologiaRicercaDAO ricercheDAO = new CronologiaRicercaPostgreDAO();


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

        return buildJson(estates);
    }

    private static String buildJson(List<Estate> estates) {
        String json = "{\"code\": 0, \"message\": \"success of action get estate\", \"Estates\": [";

        for (Estate estate : estates){
            json = json.concat(estate.TranslateToJson());
            if(!estate.equals(estates.getLast()))
                json = json.concat(",");
        }


        json = json + "]}";
        return json;
    }

    @Override
    public void close() {
        estateDAO.close();
    }

    private double haversineKm(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0; // Raggio medio terrestre in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distanzaKm = R * c;

        return distanzaKm;
    }


    @Override
    public String search(EstateFilter filter, String email) {
        List<Estate> estates;

        try {
            estates = estateDAO.search(filter);
            if (filter.getRaggio() != null && filter.getLongCentroCirconferenza() != null && filter.getLatCentroCirconferenza() != null) {
                final double latCentro = filter.getLatCentroCirconferenza();
                final double lonCentro = filter.getLongCentroCirconferenza();
                final double raggioKm = filter.getRaggio(); // raggio già in KM

                estates = estates.stream()
                        .filter(e -> {
                            Double lat = e.getIndirizzo().getLatitude();
                            Double lon = e.getIndirizzo().getLongitude();

                            double distanzaKm = haversineKm(latCentro, lonCentro, lat, lon);
                            return distanzaKm <= raggioKm;
                        })
                        .toList();

            }

            addHistory(filter,email);
        } catch (DietiEstateException e) {
            return "{\"code\": 10, \"message\": \"There aren't estets in this city\"}";
        }

        return buildJson(estates);
    }

    private void addHistory(EstateFilter filter, String email) throws DietiEstateException {
        String QueryParams = buildCmd(filter);
        System.out.println(QueryParams);
        System.out.println(email);
        ricercheDAO.add(QueryParams,email);



    }

    private String buildCmd(EstateFilter filter) {
        String QueryParams = "?page="+filter.getPage()+"?limit="+filter.getLimit()+"?sort="+filter.getSort();

        if (filter.getDesc() != null) QueryParams += "&desc=" + filter.getDesc();
        if (filter.getStato() != null && !filter.getStato().isEmpty()) QueryParams += "&stato=" + filter.getStato();
        if (filter.getCitta() != null && !filter.getCitta().isEmpty()) QueryParams += "&citta=" + filter.getCitta();
        if (filter.getQuartiere() != null && !filter.getQuartiere().isEmpty()) QueryParams += "&quartiere=" + filter.getQuartiere();
        if (filter.getVia() != null && !filter.getVia().isEmpty()) QueryParams += "&via=" + filter.getVia();
        if (filter.getMinPrice() != null) QueryParams += "&minPrice=" + filter.getMinPrice();
        if (filter.getMaxPrice() != null) QueryParams += "&maxPrice=" + filter.getMaxPrice();
        if (filter.getMinSpace() != null) QueryParams += "&minSpace=" + filter.getMinSpace();
        if (filter.getMaxSpace() != null) QueryParams += "&maxSpace=" + filter.getMaxSpace();
        if (filter.getMinRooms() != null) QueryParams += "&minRooms=" + filter.getMinRooms();
        if (filter.getMaxRooms() != null) QueryParams += "&maxRooms=" + filter.getMaxRooms();
        if (filter.getWc() != null) QueryParams += "&wc=" + filter.getWc();
        if (filter.getElevator() != null) QueryParams += "&elevator=" + filter.getElevator();
        if (filter.getState() != null && !filter.getState().isEmpty()) QueryParams += "&state=" + filter.getState();
        if (filter.getGarage() != null) QueryParams += "&garage=" + filter.getGarage();
        if (filter.getEnergeticClass() != null && !filter.getEnergeticClass().isEmpty()) QueryParams += "&energeticClass=" + filter.getEnergeticClass();
        if (filter.getMode() != null && !filter.getMode().isEmpty()) QueryParams += "&mode=" + filter.getMode();
        if (filter.getLongCentroCirconferenza() != null) QueryParams += "&longCentroCirconferenza=" + filter.getLongCentroCirconferenza();
        if (filter.getLatCentroCirconferenza() != null) QueryParams += "&latCentroCirconferenza=" + filter.getLatCentroCirconferenza();
        if (filter.getRaggio() != null) QueryParams += "&raggio=" + filter.getRaggio();

        return QueryParams;

    }


}
