import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/main.dart';


class AdminHomeController {
  //static final String urlEstates = 'http://10.0.2.2:7004/api/';
  static final String urlAdmin = 'http://127.0.0.1:7003/ManagementAdmin/'; //Per Mimmo
  
  // static Utente utente = MyApp.user;
  static Amministratore utente = loggedUser;

    static final ButtonStyle clickable_style_button = ButtonStyle(
    backgroundColor: WidgetStateProperty.all(MyApp.blu),
    foregroundColor: WidgetStateProperty.all(Colors.white),
  );

  static final ButtonStyle not_clickable_style_button = ButtonStyle(
    backgroundColor: WidgetStateProperty.all(MyApp.celeste),
    foregroundColor: WidgetStateProperty.all(Colors.white),
  );


  static Future<List<Amministratore>> getAmministratori(dynamic context, String quary) async{
    http.Response response;
    print("\n\nCIAOOO\n\n");
    Uri uri = Uri.parse(
      urlAdmin + 'loadAdmin?codicePartitaIVA=' + (utente.partitaiva ?? "0") + quary
    );
    print("\n\MERDAAA\n\n");
    try {
      print('\n\n\n\n');
      print(uri);
      print('\n\n\n\n');
      response = await http.get(
        uri, // URL valido
        headers: {"Content-Type": "application/json"},
      ); 
    }catch (e) {
      return List<Amministratore>.empty();
    }

    dynamic ris;
    List<Amministratore> amministratori = [];
    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        for (int i = 0; i < ris['admins'].length; i++) {
          amministratori.add(Amministratore.fromJson(ris['admins'][i]));
        }
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
    } catch (e) {
      return List<Amministratore>.empty();
    }
    return amministratori;
  }

 
}
