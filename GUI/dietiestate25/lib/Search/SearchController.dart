import 'dart:convert';
import 'dart:ui';

import 'package:dietiestate25/Connection/Connection.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/Model/Estate/Ricerca.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class SearchController {
  //static final String baseUrl = 'http://127.0.0.1:8000/Search/';
  //static final String baseUrl = 'http://10.0.2.2:8000/Search/';

  static int minPrice = 0;

  static int maxPrice = 0;

  static int minDimensione = 0;

  static int maxDimensione = 0;

  static int minStanze = 0;

  static int maxStanze = 0;

  static int bagni = 0;

  static String ascensore = "Tutto";

  static String stato = "Tutto";

  static int garage = 0;

  static String opzioni = "Tutto";

  static int page = 1;

  static String citta = "";

  static String mode = "Affitto";

  static double? latitudine;

  static double? longitudine;

  static double? radius;

  static Future<List> searchCity(BuildContext context, String city) async {
    if (city.isEmpty) {
      MyApp.mostraPopUpWarining(context, "Errore", "Inserisci una città");
      return List.empty();
    }

    String address =
        'Search/suggestionCities?citta=$city&quartiere=$city&stato=$city&via=$city';
  
     http.Response? response = await Connection.makeGetRequest(address);

    if (response == null) {
      MyApp.mostraPopUpWarining(context, "Errore",
          "Impossibile contattare il server, riprova tra poco");

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
      MyApp.mostraPopUpWarining(
          context, "Errore", "Controlla la tua connessione");

      return List.empty();
    }

  }
  
  static Future<List<Estate>> filteredSearch (
      BuildContext context,
      int page,
      String mode,
      int minPrice,
      int maxPrice,
      int minDimensione,
      int maxDimensione,
      int minStanze,
      int maxStanze,
      int bagni,
      String ascensore,
      String stato,
      int garage,
      String opzioni,
      String city,
      double? latitudine,
      double? longitudine,
      double? radius
      ) async {
    SearchController.citta = city;
    SearchController.page = page;
    String address =
        'Search/estates?limit=3&sort=idimmobile&desc=true&page=$page';

    SearchController.minPrice = minPrice;
    if (city.isNotEmpty) {
      address += '&city=$city';
    }

    SearchController.citta = city;
    if (minPrice != 0) {
      address += '&minPrice=$minPrice';
    }

    SearchController.maxPrice = maxPrice;
    if (maxPrice != 0) {
      address += '&maxPrice=$maxPrice';
    }

    SearchController.minDimensione = minDimensione;
    if (minDimensione != 0) {
      address += '&minSpace=$minDimensione';
    }

    SearchController.maxDimensione = maxDimensione;
    if (maxDimensione != 0) {
      address += '&maxSpace=$maxDimensione';
    }

    SearchController.minStanze = minStanze;
    if (minStanze != 0) {
      address += '&minRooms=$minStanze';
    }

    SearchController.maxStanze = maxStanze;
    if (maxStanze != 0) {
      address += '&maxRooms=$maxStanze';
    }

    SearchController.bagni = bagni;
    if (bagni != 0) {
      address += '&wc=$bagni';
    }

    SearchController.ascensore = ascensore;
    if (!ascensore.contains("Tutto")) {
      address += '&elevator=${ascensore == "Si" ? true : false}';
    }

    SearchController.stato = stato;
    if (!stato.contains("Tutto")) {
      address += '&state=$stato';
    }

    SearchController.garage = garage;
    if (garage != 0) {
      address += '&garage=$garage';
    }

    SearchController.opzioni = opzioni;
    if (!opzioni.contains("Tutto")) {
      address += '&energeticClass=$opzioni';
    }

    //
    SearchController.latitudine = latitudine;
    if (latitudine != null) {
      address += '&latCentroCirconferenza=$latitudine';
    }
    SearchController.longitudine = longitudine;
    if (longitudine != null) {
      address += '&longCentroCirconferenza=$longitudine';
    }
    SearchController.radius = radius;
    if (radius != null) { 
      final km = radius / 1000.0; // Converti metri in chilometri
      address += '&raggio=$km';
    }
    //

    SearchController.mode = mode;
    address += '&mode=$mode';

    http.Response? response = await Connection.makeGetRequest(address);


    if (response == null) {
      MyApp.mostraPopUpWarining(context, "Errore",
          "Impossibile contattare il server, riprova tra poco");

      return List.empty();
    }

    dynamic ris;
    List<Estate> estates = [];
    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        for (int i = 0; i < ris['Estates'].length; i++) {
          // print('\n\nMsg ricevuto : \n${ris['Estates'][i]}\n\n');
          estates.add(Estate.fromJson(ris['Estates'][i]));
          // print('\n\nMsg ricevuto : \n${estates[i]}\n\n');
        }
        RouteWindows.estates = estates;
        if (page != 1) {
          Navigator.pop(context);
        }
        Navigator.of(context).pushNamed(RouteWindows.estatesViewWindow);
        return estates;
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
      // }
    } catch (e) {
      MyApp.mostraPopUpWarining(context, "Errore",
          "Impossibile contattare il server, riprova tra poco");

      return List.empty();
    }
    return List.empty();
  }

    static Future<List> prevHistory(dynamic context, String comando) async {
    String address = '/Search/estates?comando=$comando';

    http.Response? response = await Connection.makeGetRequest(address);

    if (response == null) {
      MyApp.mostraPopUpWarining(context, "Errore",
          "Impossibile contattare il server, riprova tra poco");

      return List.empty();
    }

    dynamic ris;
    List<Estate> estates = [];
    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        for (int i = 0; i < ris['Estates'].length; i++) {
          // print('\n\nMsg ricevuto : \n${ris['Estates'][i]}\n\n');
          estates.add(Estate.fromJson(ris['Estates'][i]));
          // print('\n\nMsg ricevuto : \n${estates[i]}\n\n');
        }
        RouteWindows.estates = estates;
        if (page != 1) {
          Navigator.pop(context);
        }
        Navigator.of(context).pushNamed(RouteWindows.estatesViewWindow);
        return estates;
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }

      // }
    } catch (e) {
      MyApp.mostraPopUpWarining(context, "Errore",
          "Impossibile contattare il server, riprova tra poco");

      return List.empty();
    }
    return List.empty();
  }


  static Future<List<Ricerca>> getRicerche(BuildContext context) async {
    String address = '/Search/historySearch?email=${loggedUser.email}';

    http.Response? response = await Connection.makeGetRequest(address);
  

    if (response == null) {
      MyApp.mostraPopUpWarining(context, "Errore",
          "Impossibile contattare il server, riprova tra poco");
      return List.empty();
    }
    dynamic ris;
    List<Ricerca> ricerche = [];
    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        for (int i = 0; i < ris['HistorySearch'].length; i++) {
          ricerche.add(Ricerca.fromJson(ris['HistorySearch'][i]));
        }
        return ricerche;
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
    } catch (e) {
      MyApp.mostraPopUpWarining(context, "Errore",
          "Impossibile contattare il server, riprova tra poco");
    }
    


    return ricerche;
  }
  

    
}
