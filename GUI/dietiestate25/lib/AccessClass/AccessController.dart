import 'dart:convert';
import 'dart:io';
import 'package:dietiestate25/AgentHome/AgentHomeController.dart';
import 'package:dietiestate25/Home/HomeController.dart';
import 'package:dietiestate25/Home/HomeWindow.dart';
import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:logger/logger.dart';
import 'package:path_provider/path_provider.dart';
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
import 'package:google_sign_in/google_sign_in.dart';

import 'package:dietiestate25/Connection/Connection.dart';

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class AccessController {
  //static final url = Uri.parse("http://api.florindodev.site/makeLogin");
  //static final url = Uri.parse("http://127.0.0.1:7001/login/makeLogin");
  //static final url = Uri.parse("http://10.0.2.2:7001/login/makeLogin");

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
    print(jsonEncode(utente.toJson()));

    http.Response? response = await Connection.makeLogin(utente.toJson());
    // response: JWT

    if (response != null) {
      return json.decode(response.body);
    }
    return null;
  }

  static bool toLogin(Utente utente, dynamic context) {
    valida.validateEmail(utente.email);
    valida.validatePassword(utente.password);

    richiestaLogin(utente).then((risultato) {
      //risultato = json.decode(risultato);
      if (risultato == null) {
        MyApp.mostraPopUpWarining(context, "Attenzione",
            "Servizio non attualmente disponibile, prova tra qualche minuto");
        return false;
      } else if (risultato['code'] == 1) {
        MyApp.mostraPopUpWarining(context, "Attenzione", risultato['message']);
        return false;
      } else if (risultato['code'] == 0) {
        screenShooser(risultato, utente, context);

        return true;
      }
    });

    return false;
  }

  static void screenShooser(risultato, Utente utente, context) {
    final jwtPlayload = payload(risultato['message'].split('.')[1]);
    String email = jwtPlayload["sub"];
    scriviJWTInFile(risultato['message']);

    logger.d("Email: $email\nTipo di Utente Loggato è ${jwtPlayload["kid"]}");

    if (jwtPlayload["kid"] == "acquirente") {
      ProfileController.getProfile(email, Acquirente);
      Navigator.of(context).pop();
      Navigator.of(context).pushNamed(RouteWindows.homeWindow);

    } else if (jwtPlayload["kid"] == "agent") {

      ProfileController.getProfile(email, AgenteImmobiliare);
      Navigator.of(context).pop();
      Navigator.of(context).pushNamed(RouteWindows.agentHomeWindow);
    }

    MyApp.mostraPopUpInformativo(context, "Complimenti", "Login Effettuato!");
  }

  static Map payload(String jwt) {
    // La parte centrale è il payload
    final payload = jwt;

    // Assicurati che la stringa Base64 abbia il padding corretto
    final normalizedPayload = base64Url.normalize(payload);

    // Decodifica il payload da Base64Url e poi da JSON
    final payloadMap =
        json.decode(utf8.decode(base64Url.decode(normalizedPayload)));

    return payloadMap;
  }

  static Future<void> scriviJWTInFile(jwt) async {
    final directory = await getApplicationDocumentsDirectory();
    final file = File('${directory.path}/dietiEstate25.json');
    if ((await file.exists())) {
      String content = await file.readAsString();
      dynamic json = jsonDecode(content);
      print(jsonEncode(json));
      json['JWT'] = jwt;
      logger.d("JWT salvato: ${json}");
      await file.writeAsString(jsonEncode(json));
    } else {}
    await file.writeAsString("{ \"JWT\":\"$jwt\" }");
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
    return MaterialPageRoute(
        builder: (_) => SingUpWindow(appbar: MyApp.appBarNotBackable));
  }

  static MaterialPageRoute<dynamic> goToLoginWindow() {
    return MaterialPageRoute(
        builder: (_) => LoginWindow(appbar: MyApp.appBarNotBackable));
  }

  static MaterialPageRoute<dynamic> goToCreateAgencyWindow() {
    return MaterialPageRoute(
        builder: (_) => CreateAgencyWindow(appbar: MyApp.appBarNotBackable));
  }

  // Future<void> readAssetFile() async {
  //   String content = await rootBundle.loadString('assets/myfile.txt');
  //   print(content);
  // }

  static Future<String> checkLogin() async {
    try {
      http.Response? response = await Connection.validateJwtRequest();

      logger.d(
          "[AccessController] RICHIESTA PRE LOGIN: \nbody: ${response?.body}\nhead: ${response?.headers}\ncode: ${response?.statusCode}");

      if (response != null && response.statusCode == 200) {
        // Dividi il token in 3 parti
        List<String> parts = Connection.jwt!.split('.');
        if (parts.length != 3) {
          throw Exception("Token non valido");
        }

        String payloadBase64 = parts[1];
        String normalized = base64Url.normalize(payloadBase64);
        String decoded = utf8.decode(base64Url.decode(normalized));
        Map<String, dynamic> payloadMap = json.decode(decoded);

        String email = payloadMap["sub"];

        logger.d("Email: $email\nTipo di Utente Loggato è ${payloadMap["kid"]}");

        if (payloadMap["kid"] == "acquirente") {

          ProfileController.getProfile(email, Acquirente);
          return "HomeWindow";

        } else if (payloadMap["kid"] == "agent") {

          ProfileController.getProfile(email, AgenteImmobiliare);
          return "AgentHomeWindow";

        }

        return "false";
      } else {
        return "false";
      }
    } catch (e) {
      logger.e("Errore: $e");
      return "false";
    }
  }

  // GoogleSignIn instance
  final GoogleSignIn _googleSignIn = GoogleSignIn(
      scopes: ['email', 'profile', 'openid'],
      serverClientId:
          '602800046237-coq03uq25dau8bv7lop0il5h6a8cbr12.apps.googleusercontent.com');

  Future<void> handleGoogleSignUp(dynamic context) async {
    try {
      final GoogleSignInAccount? account = await _googleSignIn.signIn();
      if (account == null) return; // user aborted
      final GoogleSignInAuthentication auth = await account.authentication;

      // send idToken to backend for verification and signup
      Uri uri = Uri.parse('http://10.0.2.2:7001/signup/google');
      final response = await http.post(
        uri,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'idToken': auth.idToken}),
      );

      final body = jsonDecode(response.body);
      print(response.body);
      if (body['code'] == 0) {
        MyApp.mostraPopUpInformativo(
            context, 'Google SignUp eseguito con Successo!', body['message']);
        // salva il JWT e proced
      } else {
        MyApp.mostraPopUpWarining(
            context, 'Errore Google SignUp', body['message']);
      }
    } catch (error) {
      MyApp.mostraPopUpWarining(
          context, 'Errore Google SignUp', error.toString());
    }
  }

  Future<void> handleGoogleLogIn(dynamic context) async {
    try {
      final GoogleSignInAccount? account = await _googleSignIn.signIn();
      if (account == null) return; // user aborted
      final GoogleSignInAuthentication auth = await account.authentication;

      // send idToken to backend for verification and signup
      Uri uri = Uri.parse('http://10.0.2.2:7001/login/google');
      final response = await http.post(
        uri,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'idToken': auth.idToken}),
      );

      final body = jsonDecode(response.body);
      print(response.body);
      if (body['code'] == 0) {
        Utente utente = Utente.builder.build();
        screenShooser(body, utente, context);
      } else {
        MyApp.mostraPopUpWarining(
            context, 'Errore Google Login', body['message']);
      }
    } catch (error) {
      MyApp.mostraPopUpWarining(
          context, 'Errore Google Login', error.toString());
    }
  }
}
