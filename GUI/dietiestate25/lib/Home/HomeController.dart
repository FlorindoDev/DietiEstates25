import 'dart:convert';
import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentAccept.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentPending.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentReject.dart';
import 'package:dietiestate25/Model/Notify/AppointmentRejected.dart';
import 'package:dietiestate25/Model/Notify/AppoitmentAccepted.dart';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Model/Notify/Notify.dart';
import 'package:dietiestate25/main.dart';

class HomeController {
  static final String urlNotify = 'http://10.0.2.2:7008/api/notifies/acquirente';

  static final String urlAppointment = 'http://10.0.2.2:7006/api/appointments/acquirente';

  static late Utente utente;

  static Future<List<Notify>> getNotify(dynamic context) async {
    http.Response response;
    Uri uri = Uri.parse(urlNotify + '?email=' + utente.email + '&orderbydate=true');

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
        MyApp.mostraPopUpInformativo(context, "Errore", ris['message']);
      }
    } catch (e) {
      return List<Notify>.empty();
    }
    return notifies;
  }

  static Future<List<Appointment>> getAppointment(dynamic context, String query) async {
    http.Response response;
    Uri uri = Uri.parse(urlAppointment + '?email=' + utente.email + '&orderbydate=true' + query);

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
            Appointments.add(AppointmentPending.fromJson(ris['Appointments'][i]));
          } else if (ris['Appointments'][i]['esito'] == 'Rifiutato') {
            Appointments.add(AppointmentReject.fromJson(ris['Appointments'][i]));
          } else if (ris['Appointments'][i]['esito'] == 'Accettato') {
            Appointments.add(AppointmentAccept.fromJson(ris['Appointments'][i]));
          }
        }
      } else {
        MyApp.mostraPopUpInformativo(context, "Errore", ris['message']);
      }
    } catch (e) {
      return List<Appointment>.empty();
    }
    return Appointments;
  }
}
