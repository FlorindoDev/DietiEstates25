import 'dart:convert';
import 'package:dietiestate25/Model/Notify/AppointmentRejected.dart';
import 'package:dietiestate25/Model/Notify/AppoitmentAccepted.dart';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Model/Notify/Notify.dart';
import 'package:dietiestate25/main.dart';

class HomeController {
  static final String url = 'http://10.0.2.2:7008/notify/getNotifyAcquirente';

  static late final Utente utente;

  static Future<List<Notify>> getNotify(dynamic context) async {
    http.Response response;
    Uri uri = Uri.parse(url + '?email=' + utente.email + '&orderbydate=true');

    try {
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
          if (ris['Notify'][i]['message'] == 'Il tuo appuntamento è stato accettato') {
            notifies.add(Appoitmentaccepted.fromJson(ris['Notify'][i]));
          } else if (ris['Notify'][i]['message'] == 'Il tuo appuntamento è stato rifiutato') {
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
}
