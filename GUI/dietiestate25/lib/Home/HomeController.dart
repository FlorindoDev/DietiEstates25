import 'dart:convert';
import 'package:dietiestate25/Connection/Connection.dart';
import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentNotification.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentAccept.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentPending.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentReject.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/Model/Notify/AppointmentRejected.dart';
import 'package:dietiestate25/Model/Notify/AppoitmentAccepted.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Model/Notify/Notify.dart';
import 'package:dietiestate25/main.dart';

class HomeController {
  // static final String urlNotify = 'http://10.0.2.2:7008/api/notifies/acquirente'; // Per Android
  static final String urlNotify =
      'http://127.0.0.1:7008/api/notifies/acquirente'; // Per Windows

  // static final String urlAppointment = 'http://10.0.2.2:7006/api/appointments/acquirente'; // Per Android
  static final String urlAppointment =
      'http://127.0.0.1:7006/api/appointments/acquirente'; // Per Windows

  // static final String urlAppointmentSpecific ='http://10.0.2.2:7006/api/appointments'; // Per Android
  static final String urlAppointmentSpecific =
      'http://127.0.0.1:7006/api/appointments'; // Per Windows

  static final String urlSearch = "Search";

  // static Utente utente = MyApp.user;
  static Utente utente = loggedUser;

  static Future<List<Notify>> getNotify(dynamic context) async {
    http.Response response;
    Uri uri =
        Uri.parse(urlNotify + '?email=' + utente.email + '&orderbydate=true');

    try {
      print(uri);
      // Fai la richiesta HTTP
      response = await http.get(
        uri, // URL valido
        headers: {"Content-Type": "application/json"},
      ); //as http.Response;
    } catch (e) {
      return List<Notify>.empty();
    }

    // Controlla se lo statusCode non è 200 (OK)
    dynamic ris;
    List<Notify> notifies = [];
    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        for (int i = 0; i < ris['Notify'].length; i++) {
          if (ris['Notify'][i]['tipo'] == 'Appuntamento Accettato') {
            notifies.add(Appoitmentaccepted.fromJson(ris['Notify'][i]));
          } else if (ris['Notify'][i]['tipo'] == 'Appuntamento Rifiutato') {
            notifies.add(Appointmentrejected.fromJson(ris['Notify'][i]));
          }
        }
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
    } catch (e) {
      return List<Notify>.empty();
    }
    return notifies;
  }

  static Future<List<Appointment>> getAppointment(
      dynamic context, String query) async {
    http.Response response;
    Uri uri = Uri.parse(urlAppointment +
        '?email=' +
        utente.email +
        '&orderbydate=true' +
        query);

    try {
      print(uri);
      // Fai la richiesta HTTP
      response = await http.get(
        uri, // URL valido
        headers: {"Content-Type": "application/json"},
      ); //as http.Response;
    } catch (e) {
      return List<Appointment>.empty();
    }

    // Controlla se lo statusCode non è 200 (OK)
    dynamic ris;
    List<Appointment> Appointments = [];
    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        for (int i = 0; i < ris['Appointments'].length; i++) {
          if (ris['Appointments'][i]['esito'] == 'Da decidere') {
            Appointments.add(
                AppointmentPending.fromJson(ris['Appointments'][i]));
          } else if (ris['Appointments'][i]['esito'] == 'Rifiutato') {
            Appointments.add(
                AppointmentReject.fromJson(ris['Appointments'][i]));
          } else if (ris['Appointments'][i]['esito'] == 'Accettato') {
            Appointments.add(
                AppointmentAccept.fromJson(ris['Appointments'][i]));
          }
        }
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
    } catch (e) {
      return List<Appointment>.empty();
    }
    return Appointments;
  }

  static Future<AppointmentNotification> getAppointmentSpecific(
      dynamic context, String idAppointment) async {
    http.Response response;
    Uri uri = Uri.parse(urlAppointmentSpecific +
        '?id=' +
        idAppointment +
        '&extended=true' +
        '&agent=false');

    AppointmentNotification Appointments = new AppointmentNotification(
        idAppointment: 0,
        data: "0",
        esito: "0",
        dataRichesta: "0",
        idAcquirente: 0,
        idEstate: 0,
        nomeEcognome: "0",
        viaEstate: "0");
    try {
      print(uri);
      // Fai la richiesta HTTP
      response = await http.get(
        uri, // URL valido
        headers: {"Content-Type": "application/json"},
      ); //as http.Response;
    } catch (e) {
      return Appointments;
    }

    // Controlla se lo statusCode non è 200 (OK)
    dynamic ris;

    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      print(ris['Appointment']);
      print(ris['code']);
      if (ris['code'] == 0) {
        Appointments = AppointmentNotification.fromJson(ris['Appointment']);
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
    } catch (e) {
      return Appointments;
    }
    return Appointments;
  }

  static Future<List<Estate>> getEstatesHome(
      BuildContext context, String quary) async {
    http.Response? response = await Connection.makeGetRequest(
        urlSearch + '/estates?sort=idimmobile&desc=true' + quary);

    if (response == null) {
      return List<Estate>.empty();
    }

    dynamic ris;
    List<Estate> estates = [];
    try {
      // if (response != null) {

      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        for (int i = 0; i < ris['Estates'].length; i++) {
          //print('\n\nMsg ricevuto : \n${ris['Estates'][i]['foto']}\n\n');
          estates.add(Estate.fromJson(ris['Estates'][i]));
        }
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
      // }
    } catch (e) {
      return List<Estate>.empty();
    }
    //print("\n\n\n\nCiaooooo");
    //print(estates);
    //print("\n\n\n\nCiaooooo");
    return estates;
  }
}
