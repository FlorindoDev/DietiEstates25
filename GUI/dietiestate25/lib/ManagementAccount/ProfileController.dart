import 'dart:convert';
import 'dart:io';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/Connection/Connection.dart';
import 'package:dietiestate25/main.dart';
import 'package:path_provider/path_provider.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class ProfileController {
  // static Utente utente = MyApp.user;

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

  Future<bool> fetchProfile() async {
    logger.d("[ProfileController] User Data: ${loggedUser.toJson()}");
    http.Response? response = await Connection.makeGetRequest(
        "${Connection.getAcquirentProfileUrl}?email=${loggedUser.email}");
    logger.d(
        "[ProfileController] Profile Data: ${response?.body} ${response?.statusCode}");

    if (response == null || response.statusCode != 200) {
      logger.e("Stam a sott");
      return false;
    }
    return true;
  }
}

// Gli utenti devono poter modificare i loro dati personali: nome, cognome, e-mail, password. Gli 
// agenti immobiliari devono avere un profilo più dettagliato, con informazioni professionali (e.g.: una 
// breve biografia) e magari una valutazione lasciata dai clienti. È gradita la possibilità di aggiungere 
// anche delle foto per gli agenti immobiliari, per rendere i profili più “umani”. 