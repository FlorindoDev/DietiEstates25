import 'dart:convert';

import 'package:dietiestate25/Connection/Connection.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class SearchController {
  
  
  static final String baseUrl = 'http://127.0.0.1:7012/Search/';

  static Future<List> searchCity(BuildContext context, String city) async {
    
    if (city.isEmpty) {
      MyApp.mostraPopUpWarining(context, "Errore", "Inserisci una città");
      return List.empty();
    }

    Uri uri = Uri.parse('${baseUrl}suggestionCities?citta=$city&quartiere=$city&stato=$city&via=$city');

    // Fai la richiesta HTTP
    http.Response response;
    try {
      response = await http.get(
        uri, // URL valido
        headers: {
          "Content-Type": "application/json",
          "Authorization": 'Bearer ${Connection.jwt}',
        },
      );
    } catch (e) {
      MyApp.mostraPopUpWarining(context, "Errore", "Impossibile contattare il server");
      return List.empty();
    }

    

    // Controlla se lo statusCode non è 200 (OK)
    dynamic ris;
    List cities = [];
    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (response.statusCode != 200) {
        // Controlla se lo statusCode non è 200 (OK)
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
        return List.empty();
      }

      return ris;
      
    } catch (e) {
      MyApp.mostraPopUpWarining(context, "Errore", "Controlla la tua connessione");
      return List.empty();
    }
  
  

  }
  










}