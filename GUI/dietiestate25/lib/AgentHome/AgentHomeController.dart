import 'dart:convert';
import 'dart:ffi';
import 'package:dietiestate25/Model/Appointment/AppointmentNotification.dart';
import 'package:dietiestate25/Model/Notify/AppointmentPending.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Model/Notify/Notify.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentAccept.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentPending.dart'
    as Model;
import 'package:dietiestate25/Model/Appointment/AppointmentReject.dart';

class AgentHomeController {
  static final String urlEstates = 'http://10.0.2.2:7004/api/';

  static final String urlNotify = 'http://10.0.2.2:7008/api/notifies/agent';

  static final String urlAppointment =
      'http://10.0.2.2:7006/api/appointments/agent';

  static final String urlAppointmentSpecific =
      'http://10.0.2.2:7006/api/appointments';

  static final String urlAppointmentUpdate =
      'http://10.0.2.2:7006/api/appointments';

  static late final Utente utente;

  static Future<List<Estate>> getEstate(dynamic context) async {
    http.Response response;
    Uri uri = Uri.parse(urlEstates);

    try {
      print(uri);
      // Fai la richiesta HTTP
      response = await http.get(
        uri, // URL valido
        headers: {"Content-Type": "application/json"},
      ); //as http.Response;
    } catch (e) {
      return List<Estate>.empty();
    }


    List<Estate> estates = [];
    return estates;
    /*
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
    return notifies;*/
  }

  static Future<List<Notify>> getNotify(dynamic context) async {
    http.Response response;
    Uri uri =
        Uri.parse(urlNotify + '?email=' + utente.email + '&orderbydate=true');

    print(uri);
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
          if (ris['Notify'][i]['tipo'] == 'Appuntamento Da Confermare') {
            ris['Notify'][i]['message'] =
                "Questo appuntamento deve essere confermato";
            notifies.add(AppointmentPending.fromJson(ris['Notify'][i]));
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
                Model.AppointmentPending.fromJson(ris['Appointments'][i]));
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
    Uri uri = Uri.parse(
        urlAppointmentSpecific + '?id=' + idAppointment + '&extended=true');

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

  static Future<bool> updateAppointment(
      dynamic context, bool accettato, Appointment appointment) async {
    http.Response response;
    Uri? uri;
    String? body;
    if (accettato == true) {
      uri = Uri.parse(urlAppointmentSpecific + "/acceptAppointment");
      body = jsonEncode(appointment.toJson("Accettato"));
    } else {
      uri = Uri.parse(urlAppointmentSpecific + "/declineAppointment");
      body = jsonEncode(appointment.toJson("Rifiutato"));
    }
    bool esito = false;
    print(body);
    try {
      print(uri);
      // Fai la richiesta HTTP
      response = await http.post(
        uri,
        headers: {
          "Content-Type": "application/json",
          // Aggiungi altri header se necessario (es: token auth)
        },
        body: body, // Codifica l'oggetto in JSON
      ); //as http.Response;
    } catch (e) {
      MyApp.mostraPopUpWarining(
          context, "Errore", "Qualcosa è adato storto durante l'operazione");
      return esito;
    }

    dynamic ris;

    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        MyApp.mostraPopUpInformativo(context, "Riuscito", ris['message']);
        esito = true;
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
    } catch (e) {
      return esito;
    }
    return esito;
  }
}
