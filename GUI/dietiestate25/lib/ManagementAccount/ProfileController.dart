import 'dart:convert';
import 'dart:io';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart' as http;

import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/Connection/Connection.dart';
import 'package:path_provider/path_provider.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class ProfileController {
  static bool cached = false;
  static bool cacheExpired = true;
  static String? email;

  ProfileController._(); // Blocca l'istanziazione

  static Future<void> resetJWT() async {
    final directory = await getApplicationDocumentsDirectory();
    final file = File('${directory.path}/dietiEstate25.json');
    if (!(await file.exists())) {
      String content = await file.readAsString();
      dynamic json = jsonDecode(content);
      json['JWT'] = "";
      await file.writeAsString(jsonEncode(json));
    } else {}
    await file.writeAsString('{ "JWT":"" }');
  }
  // static Future<void> resetJWT() async {
  //   final directory = await getApplicationDocumentsDirectory();
  //   final file = File('${directory.path}/dietiEstate25.json');
  //   if (await file.exists()) {
  //     String content = await file.readAsString();
  //     dynamic json = jsonDecode(content);
  //     json['JWT'] = "";
  //     await file.writeAsString(jsonEncode(json));
  //   } else {
  //     await file.writeAsString('{ "JWT":"" }');
  //   }
  // }

  static Future<bool> getProfile(String emailIn) async {
    logger.d("[getProfile]: cached: $cached - expired: $cacheExpired");
    if (!cached || cacheExpired) {
      email = emailIn;
      logger.d("[getProfile]: executing");
      http.Response? response = await Connection.makeGetRequest(
          "${Connection.getAccountProfileUrl[loggedUserType]}?email=$email");

      // if (response != null) {
      //   if (jsonDecode(response.body)['code'] != 0) return false;
      // }

      if (response == null || response.statusCode != 200) {
        logger.e("Stam a sott");
        return false;
      }

      switch (loggedUserType) {
        case UserType.agent:
          loggedUser = AgenteImmobiliare.fromJson(jsonDecode(response.body));

        default:
          logger.e("sometyng gone wrong");
          return false;
      }

      cached = true;
      cacheExpired = false;
    } else {
      logger.i("No need fetch, cached data are fetched");
    }

    return true;
  }

  // Future<bool> fetchProfile() async {
  //   logger.d("[ProfileController] User Data: ${loggedUser.toJson()}");
  //   http.Response? response = await Connection.makeGetRequest(
  //       "${Connection.getAccountProfileUrl[loggedUserType]}?email=${loggedUser.email}");
  //   logger.d(
  //       "[ProfileController] url: ${Connection.getAccountProfileUrl[loggedUserType]}\nProfile Data: ${response?.body} ${response?.statusCode}");

  //   // if (response != null) { // da fixare
  //   //   if (jsonDecode(response.body)['code'] != 0) return false;
  //   // }

  //   if (response == null || response.statusCode != 200) {
  //     logger.e("Stam a sott");
  //     return false;
  //   }

  //   return true;
  // }

  static Future<bool> refreshProfile() async {
    logger.d("[refreshProfile]: executing");

    http.Response? response = await Connection.makeGetRequest(
        "${Connection.getAccountProfileUrl[loggedUserType]}?email=$email");

    if (response == null || response.statusCode != 200) {
      logger.e("Stam a sott");
      return false;
    }

    switch (loggedUserType) {
      case UserType.agent:
        loggedUser = AgenteImmobiliare.fromJson(jsonDecode(response.body));
        logger.i(response.body);
        logger.i(loggedUser.toJson());

      default:
        logger.e("sometyng gone wrong");
    }

    cached = true;
    cacheExpired = false;

    return true;
  }
}

// Gli utenti devono poter modificare i loro dati personali: nome, cognome, e-mail, password. Gli 
// agenti immobiliari devono avere un profilo più dettagliato, con informazioni professionali (e.g.: una 
// breve biografia) e magari una valutazione lasciata dai clienti. È gradita la possibilità di aggiungere 
// anche delle foto per gli agenti immobiliari, per rendere i profili più “umani”. 