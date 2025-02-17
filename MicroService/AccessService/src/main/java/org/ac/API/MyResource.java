package org.ac.API;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.*;

/**
 * Root resource (exposed at "myresource" path)
 */


@Path("Evasori")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        Map<String, Object> data = new HashMap<>();
        ArrayList<Object> l = new ArrayList<>();
        Map<String, Object> data2 = new HashMap<>();
        data.put("nome", "Francesco");
        data.put("cognome", "Calculli");
        data.put("eta", 21);
        data.put("isMarried", false);
        l.add(data);
        data2.put("Evasori", l);
        // Convertilo in JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "{}";
        try {
            jsonString = objectMapper.writeValueAsString(data2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;

    }

        /*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getNames(NomeWrapper data) {

        for(String u : data.getNome()){
            System.out.println(u);
        }
        System.out.println(data.getP().get(0).getB());



        System.out.println(data.eta);
        return null;
    }

         */
}
