import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Model/Notify/Notify.dart';

class HomeController {
  static final url = Uri.parse('http://10.0.2.2:7008/notify/getNotifyAcquirente');

  static late final Utente utente;

  static Future<List<Notify>> getNotify() async {
    http.Response response;
    print(jsonEncode(utente.toJson()));
    try {
      // Fai la richiesta HTTP
      response = await http.post(
        url, // URL valido
        headers: {"Content-Type": "application/json"},
        body: jsonEncode(utente.toJson()),
      ); //as http.Response;
    } catch (e) {
      return List<Notify>.empty();
    }

    // Controlla se lo statusCode non è 200 (OK)
    dynamic ris;
    List<Notify> notifies = [];
    try {
      ris = json.decode(response.body);
      print(ris['Notify']);
      for (int i = 0; i < response.body.length; i++) {
        notifies.add(Notify.fromJson(ris['Notify'][i]));
      }
    } catch (e) {
      return List<Notify>.empty();
    }
    return notifies;
  }
}
