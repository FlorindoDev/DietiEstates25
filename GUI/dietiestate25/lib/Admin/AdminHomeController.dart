import 'dart:convert';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
// import 'package:dietiestate25/Model/loggedUser/loggedUser.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/Connection/Connection.dart';

class AdminHomeController {
  //static final String urlEstates = 'http://10.0.2.2:7004/api/';
  static final String urlAdmin = 'ManagementAdmin/'; //Per Mimmo

  
  
  static final ButtonStyle clickable_style_button = ButtonStyle(
    backgroundColor: WidgetStateProperty.all(MyApp.blu),
    foregroundColor: WidgetStateProperty.all(Colors.white),
  );

  static final ButtonStyle not_clickable_style_button = ButtonStyle(
    backgroundColor: WidgetStateProperty.all(MyApp.celeste),
    foregroundColor: WidgetStateProperty.all(Colors.white),
  );

  static Future<List<Amministratore>> getAmministratori(dynamic context, String quary) async {

    http.Response? response = await Connection.makeGetRequest(urlAdmin +  'loadAdmin?codicePartitaIVA=' + (loggedUser.partitaiva ?? "0") + quary);

    if (response == null) {
      return List<Amministratore>.empty();
    }


    dynamic ris;
    List<Amministratore> amministratori = [];
    try {
      // if (response != null) {
        ris = json.decode(utf8.decode(response.bodyBytes));
        if (ris['code'] == 0) {
          for (int i = 0; i < ris['admins'].length; i++) {     
            if(ris['admins'][i]['email'] != loggedUser.email){
              amministratori.add(Amministratore.fromJson(ris['admins'][i]));
            }
            
          }
        } else {
          MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
        }
      // }
    } catch (e) {
      return List<Amministratore>.empty();
    }
    return amministratori;
  }

  static bool isSupporto() {
    print("\n\n\n\n");
    print(loggedUser);
    print("\n\n\n\n");
    return loggedUser.issupportoammi;
  }

  static Future promoteAdmin(BuildContext context, Amministratore admin, String s) async{


    final Map<String, dynamic> userMap = admin.toJson();
    http.Response? response = await Connection.makePostRequest(userMap, urlAdmin +'upgradeSupportAdmin');
    if(response == null) {
      MyApp.mostraPopUpWarining(context, "Errore", "Errore di rete");
      return;
    }else{
      
      manageResponse(response, context);
    }


  }

  static Future unPromoteAdmin(BuildContext context, Amministratore admin, String s) async {
    final Map<String, dynamic> userMap = admin.toJson();
    http.Response? response = await Connection.makePostRequest(userMap, urlAdmin +'downgradeSupportAdmin');
    if(response == null) {
      MyApp.mostraPopUpWarining(context, "Errore", "Errore di rete");
      return;
    }else{
      
      manageResponse(response, context);
    }
    

  }

  static removeAdmin(BuildContext context, Amministratore admin, String s) async {

    http.Response? response = await Connection.makeDeleteRequest(urlAdmin +'removeAdmin?email=' + admin.email);
    if(response == null) {
      MyApp.mostraPopUpWarining(context, "Errore", "Errore di rete");
      return;
    }else{
      
      manageResponse(response, context);
    }
    



  

  }

  static void manageResponse(http.Response response, BuildContext context) {
    var responseData = json.decode(utf8.decode(response.bodyBytes));
    if (response.statusCode == 200) {
      // La richiesta è andata a buon fine
      
      if (responseData['code'] == 0) {
        MyApp.mostraPopUpSuccess(context, "Successo", responseData['message']);
       
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", responseData['message']);
      }
    } else {
      // La richiesta non è andata a buon fine
      MyApp.mostraPopUpWarining(context, "Errore", responseData['message']);
    }
  }

  static void createAdmin(BuildContext context, Amministratore admin, String s) async{
   
    final Map<String, dynamic> userMap = admin.toJson();
    http.Response? response = await Connection.makePostRequest(userMap, urlAdmin +'addAdmin');
    if(response == null) {
      MyApp.mostraPopUpWarining(context, "Errore", "Errore di rete");
      return;
    }else{
      
      manageResponse(response, context);
    }
    
  }
}
