import 'dart:convert';
import 'dart:io';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:path_provider/path_provider.dart';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AccessClass/CreateAgencyWindow.dart';
import 'package:dietiestate25/Model/Agenzia/Agenzia.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/Validation/Validetor.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/AccessClass/SingUpWindow.dart';

class AccessController {
  //static final url = Uri.parse("http://api.florindodev.site/makeLogin");
  //static final url = Uri.parse("http://127.0.0.1:7001/login/makeLogin");
  static final url = Uri.parse("http://10.0.2.2:7001/login/makeLogin");

  static String token = "";
  static Validator valida = Validate();

  static final ButtonStyle clickable_style_button = ButtonStyle(
    backgroundColor: WidgetStateProperty.all(MyApp.blu),
    foregroundColor: WidgetStateProperty.all(Colors.white),
  );

  static final ButtonStyle not_clickable_style_button = ButtonStyle(
    backgroundColor: WidgetStateProperty.all(MyApp.celeste),
    foregroundColor: WidgetStateProperty.all(Colors.white),
  );

  static Future<dynamic> richiestaLogin(Utente utente) async {
    http.Response response;

    print(jsonEncode(utente.toJson()));

    try {
      // Fai la richiesta HTTP
      response = await http.post(
        url, // URL valido
        headers: {"Content-Type": "application/json"},
        body: jsonEncode(utente.toJson()),
      ); //as http.Response;

      print("1");
    } catch (e) {
      //print(e.toString());
      // Se c'è un errore di connessione, rilancia con un messaggio specifico
      //throw Exception("Servizio non attualmente disponibile, prova tra qualche minuto");
      return null;
    }

    // Controlla se lo statusCode non è 200 (OK)
    dynamic ris;
    try {
      ris = json.decode(response.body);
    } catch (e) {
      return null;
    }
    return ris;
  }

  static bool toLogin(Utente utente, dynamic context) {
    valida.validateEmail(utente.email);
    valida.validatePassword(utente.password);
    print("eseguo prova");

    richiestaLogin(utente).then((risultato) {
      //risultato = json.decode(risultato);
      if (risultato == null) {
        MyApp.mostraPopUpInformativo(context, "Attenzione", "Servizio non attualmente disponibile, prova tra qualche minuto");
        return false;
      } else if (risultato['code'] == 1) {
        MyApp.mostraPopUpInformativo(context, "Attenzione", risultato['message']);
        return false;
      } else if (risultato['code'] == 0) {
        print('1');
        scriviJWTInFile(risultato['message']);
        print('12');
        MyApp.mostraPopUpInformativo(context, "Complimenti", "Login Effettuato!");
        print('13');
        Navigator.of(context).pop();
        Navigator.of(context).pushNamed(RouteWindows.homeWindow);
        return true;
      }
    });

    print("finito prova");
    return false;
  }

  static Future<void> scriviJWTInFile(jwt) async {
    print('2');
    final directory = await getApplicationDocumentsDirectory();
    print('2');
    final file = File('${directory.path}/dietiEstate25.json');
    print('3');
    if ((await file.exists())) {
      print('4');
      String content = await file.readAsString();
      print('5');
      dynamic json = jsonDecode(content);
      print('6');
      print(jsonEncode(json));
      print('7');
      json['JWT'] = jwt;
      print('8');
      print(jsonEncode(json));
      print('9');
      await file.writeAsString(jsonEncode(json));
      print('10');
    } else {}
    print('11');
    await file.writeAsString("{ \"JWT\":\"$jwt\" }");
    print('12');
  }

  static void toSignUp(Utente utente) {
    valida.validateName(utente.nome);
    valida.validateSurname(utente.cognome);
    valida.validateEmail(utente.email);
    valida.validatePassword(utente.password);
  }

  static void toCreateAgency(Agenzia agenzia) {
    valida.validateEmail(agenzia.email);
    valida.validateSede(agenzia.sede);
    valida.validatePartitalVA(agenzia.partitaIVA);
  }

  static MaterialPageRoute<dynamic> goToSignUpWindow() {
    return MaterialPageRoute(builder: (_) => SingUpWindow(appbar: MyApp.appBarNotBackable));
  }

  static MaterialPageRoute<dynamic> goToLoginWindow() {
    return MaterialPageRoute(builder: (_) => LoginWindow(appbar: MyApp.appBarNotBackable));
  }

  static MaterialPageRoute<dynamic> goToCreateAgencyWindow() {
    return MaterialPageRoute(builder: (_) => CreateAgencyWindow(appbar: MyApp.appBarNotBackable));
  }

  // Future<void> readAssetFile() async {
  //   String content = await rootBundle.loadString('assets/myfile.txt');
  //   print(content);
  // }

  static Future<bool> checkLogin() async {
    try {
      String content = await rootBundle.loadString('assets/storage.json');
      Map<String, dynamic> jsonData = jsonDecode(content);
      print(content);
      //INVIO A KONG PER LA VALIDAZIONE DEL JWT
      return true;
    } catch (e) {
      print("Errore: $e");
      return false;
    }
  }
}
