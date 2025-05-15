import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/Connection/Connection.dart';
import 'package:path_provider/path_provider.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class ProfileController {
  static bool _cached = false;
  static bool _cacheExpired = true;
  static String? email;

  // [OK] la password non viene criptata prima di fare l'update nel DAO
  // [OK] eliminare loggedUserType e usare loggedUser.runtimeType
  // infinite loading
  // Mod biografia
  // mod Img

  ProfileController._(); // Blocca l'istanziazione

  // static Future<void> resetJWT() async {
  //   final directory = await getApplicationDocumentsDirectory();
  //   final file = File('${directory.path}/dietiEstate25.json');
  //   if (!(await file.exists())) {
  //     String content = await file.readAsString();
  //     dynamic json = jsonDecode(content);
  //     json['JWT'] = "";
  //     await file.writeAsString(jsonEncode(json));
  //   } else {}
  //   await file.writeAsString('{ "JWT":"" }');
  // }

  static Future<void> resetJWT() async {
    final directory = await getApplicationDocumentsDirectory();
    final file = File('${directory.path}/dietiEstate25.json');
    if (await file.exists()) {
      String content = await file.readAsString();
      dynamic json = jsonDecode(content);
      json['JWT'] = "";
      await file.writeAsString(jsonEncode(json));
    } else {
      await file.writeAsString('{ "JWT":"" }');
    }
  }

  static void resetCache(){
    _cached = false;
    _cacheExpired = true;
  }

  static Future<bool> getProfile(String emailIn, [Type? userType]) async {
    logger.d("[getProfile]: cached: $_cached - expired: $_cacheExpired");
    if (!_cached || _cacheExpired) {
      email = emailIn;
      logger.d("[getProfile]: executing");

      final Type? typeToUse = userType ?? loggedUser?.runtimeType;

      if (typeToUse == null) {
        logger.e(
            "Could not determine user type. Logged user: ${loggedUser != null}, provided type: ${userType != null}");
        return false;
      }

      http.Response? response = await Connection.makeGetRequest(
          "${Connection.getAccountProfileUrl[typeToUse]}?email=$email");

      if (response == null || response.statusCode != 200) {
        logger.e("sometyng gone wrong");
        return false;
      }

      logger.d("[getProfile]: row user: ${response.body}");

      switch (typeToUse) {
        case Acquirente:
          loggedUser = Acquirente.fromJson(jsonDecode(response.body));

        case AgenteImmobiliare:
          loggedUser = AgenteImmobiliare.fromJson(jsonDecode(response.body));

        case Amministratore:
          loggedUser = Amministratore.fromJson(jsonDecode(response.body));

        default:
          logger.e("sometyng gone wrong");
      }

      logger.i(response.body);
      logger.i(loggedUser.toJson());

      _cached = true;
      _cacheExpired = false;
    } else {
      logger.i("No need fetch, cached data are fetched");
    }

    return true;
  }

  static Future<bool> refreshProfile() async {
    logger.d("[refreshProfile]: executing");

    http.Response? response = await Connection.makeGetRequest(
        "${Connection.getAccountProfileUrl[loggedUser.runtimeType]}?email=$email");

    if (response == null || response.statusCode != 200) {
      logger.e("Stam a sott");
      return false;
    }

    switch (loggedUser.runtimeType) {
      case Acquirente:
        loggedUser = Acquirente.fromJson(jsonDecode(response.body));

      case AgenteImmobiliare:
        loggedUser = AgenteImmobiliare.fromJson(jsonDecode(response.body));

      case Amministratore:
        loggedUser = Amministratore.fromJson(jsonDecode(response.body));

      default:
        logger.e("sometyng gone wrong");
    }

    logger.i(response.body);
    logger.i(loggedUser.toJson());

    _cached = true;
    _cacheExpired = false;

    return true;
  }

  static Future<bool> updateProfile(Utente user) async {
    final Map<String, dynamic> userMap = user.toJson();
    userMap.remove('sensitivity');
    logger.i("Update Profile ${userMap}");

    http.Response? response = await Connection.makePostRequest(
        userMap, Connection.updateAccountProfileUrl[loggedUser.runtimeType]);

    if (response == null) return false;

    Map<String, dynamic> jsonBody = jsonDecode(response.body) as Map<String, dynamic>;

    if (jsonBody.containsKey('code') && jsonBody["code"] != 0) {
      logger.e(jsonBody["message"]);
      return false;
    }

    _cacheExpired = true;

    return true;
  }
}

// Gli utenti devono poter modificare i loro dati personali: nome, cognome, e-mail, password. Gli 
// agenti immobiliari devono avere un profilo più dettagliato, con informazioni professionali (e.g.: una 
// breve biografia) e magari una valutazione lasciata dai clienti. È gradita la possibilità di aggiungere 
// anche delle foto per gli agenti immobiliari, per rendere i profili più “umani”. 