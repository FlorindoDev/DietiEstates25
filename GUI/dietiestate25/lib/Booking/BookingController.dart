import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Model/Tempo/WeatherInfo.dart';
import 'package:dietiestate25/Connection/Connection.dart';
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

final String url = '/api/appointments'; // Per Android
//final String url = 'http://127.0.0.1:7006/api/appointments/meteo?'; // Per Windows

class BookingController {
  // Constructor
  BookingController();

  static Future<List<WeatherInfo>> fetchWeatherList(
      dynamic context, DateTime date, String lon, String lat) async {
    final formattedDate = DateFormat('yyyy-MM-dd').format(date);
    dynamic ris;
    try {
      //final urls = Uri.parse(url +
      //'latitude=${lat}&longitude=${lon}&start_date=${formattedDate}&end_date=${formattedDate}');
      //print(urls);
      //final response = await http.get(urls);
      final response = await Connection.makeGetRequest(
          '${url}/meteo?latitude=${lat}&longitude=${lon}&start_date=${formattedDate}&end_date=${formattedDate}');
      ris = json.decode(utf8.decode(response!.bodyBytes));
      if (ris['code'] == 0) {
        if (ris["weather"]['code'] == 112 || ris["weather"]['code'] == 111)
          MyApp.mostraPopUpWarining(context, "Errore",
              'errore generato dalla richiesta: ${ris["weather"]['code_status']}');

        final Map<String, dynamic> decoded = json.decode(response.body);
        final hourly = decoded['weather']['hourly'] as Map<String, dynamic>;

        final List<dynamic> times = hourly['time']!;
        final List<dynamic> precips = hourly['precipitation']!;
        final List<dynamic> probs = hourly['precipitation_probability']!;
        final List<dynamic> clouds = hourly['cloud_cover']!;

        // tutti gli array abbiano la stessa lunghezza:
        final int count = times.length;

        final List<WeatherInfo> result = List.generate(count, (i) {
          // parse ISO8601 "2025-04-03T14:00" in locale “14:00”
          final dt = DateTime.parse(times[i] as String);
          final hourString = DateFormat.Hm('it_IT').format(dt);

          return WeatherInfo(
            time: hourString,
            rainProbability: (probs[i] as num).toInt(),
            rainAmount: (precips[i] as num).toDouble(),
            cloudCoverage: (clouds[i] as num).toInt(),
          );
        });
        return result;
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
    } catch (e) {
      return List<WeatherInfo>.empty();
    }
    return List<WeatherInfo>.empty();
  }

  static Future<bool> makeAppointment(
      dynamic context, Estate estate, DateTime? date) async {
    final formattedDate = DateFormat('yyyy-MM-dd').format(date as DateTime);
    Map<String, dynamic> body = {
      "idAppointment": 0,
      "data": formattedDate,
      "acquirente": {
        "email": loggedUser!.email,
      },
      "estate": {
        "idEstate": estate.idEstate,
      }
    };

    bool esito = false;

    http.Response? response =
        await Connection.makePostRequest(body, '${url}/makeAppointment');
    if (response == null) {
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
