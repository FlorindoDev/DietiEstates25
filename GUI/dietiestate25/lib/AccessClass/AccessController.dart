import 'dart:convert';
import 'dart:io';

import 'package:dietiestate25/AccessClass/CreateAgencyWindow.dart';
import 'package:dietiestate25/Model/Agenzia/Agenzia.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/Validation/Validetor.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/AccessClass/SingUpWindow.dart';

import 'package:http/http.dart' as http;

class AccessController {
  static final url = Uri.parse("http://api.florindodev.site/makeLogin");

  static const loginWindow = '/LoginWindow';
  static const singUpWindow = '/SingUpWindow';
  static const createAgencyWindow = '/CreateAgencyWindow';

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

  static void mostraPopUp(dynamic context, String titolo, String messaggio) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(titolo),
          icon: Icon(Icons.warning_rounded),
          content: Text(messaggio),
          actions: <Widget>[
            TextButton(
              child: Text("OK"),
              onPressed: () {
                Navigator.of(context).pop(); // Chiude il dialogo
              },
            ),
          ],
        );
      },
    );
  }

  static Future<String> richiestaLogin(Utente utente) async {
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
      print(e.toString());
      // Se c'è un errore di connessione, rilancia con un messaggio specifico
      //throw Exception("Servizio non attualmente disponibile, prova tra qualche minuto");
      return "Servizio non attualmente disponibile, prova tra qualche minuto";
    }

    // Controlla se lo statusCode non è 200 (OK)
    print(response.statusCode);
    var rispostajson = json.decode(response.body);
    if (rispostajson['code'] == 1) {
      print("10");
      print(response.body);
      return rispostajson["error"];
    }

    print("3"); // Questo viene eseguito solo se tutto va a buon fine
    return "Successo";
  }

  static void toLogin(Utente utente) {
    valida.validateEmail(utente.email);
    valida.validatePassword(utente.password);
    print("eseguo prova");
    richiestaLogin(utente);
    print("finito prova");
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

  static Route<dynamic> generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case AccessController.loginWindow:
        return goToLoginWindow();
      case AccessController.singUpWindow:
        return goToSignUpWindow();
      case AccessController.createAgencyWindow:
        return goToCreateAgencyWindow();
    }

    return MaterialPageRoute(builder: (_) => LoginWindow(appbar: MyApp.appBarNotBackable));
  }
}
