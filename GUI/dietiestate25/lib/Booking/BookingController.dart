import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Model/Tempo/WeatherInfo.dart';
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

final String url = 'http://10.0.2.2:7006/api/appointments/meteo?';

class BookingController {
  // Constructor
  BookingController();

  static Future<List<WeatherInfo>> fetchWeatherList(
      dynamic context, DateTime date, String lon, String lat) async {
    final formattedDate = DateFormat('yyyy-MM-dd').format(date);
    dynamic ris;
    try {
      final urls = Uri.parse(url +
          'latitude=${lat}&longitude=${lon}&start_date=${formattedDate}&end_date=${formattedDate}');
      print(urls);
      final response = await http.get(urls);
      ris = json.decode(utf8.decode(response.bodyBytes));
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
}
