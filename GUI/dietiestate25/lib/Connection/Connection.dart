import 'package:dietiestate25/main.dart';
import 'package:http/http.dart' as http;
import 'package:path_provider/path_provider.dart';
import 'dart:io';
import 'dart:convert';
import 'package:dietiestate25/Model/Utente/Utente.dart';

class Connection {
  // static final Uri url = Uri.parse('http://localhost:8000/'); // KONG URL
  static final String baseUrl = 'http://127.0.0.1:8000'; // Per Windows
  // static final String baseUrl = 'http://10.0.2.2:8000'; // Per Andorid

  static final Map<Type, String> getAccountProfileUrl = {
    Acquirente: '/ManagementAccount/getAccountAcquirente',
    AgenteImmobiliare: '/ManagementAccount/getAccountAgent',
    Amministratore: '/ManagementAccount/getAccountAdmin'
  };

  static final Map<Type, String> updateAccountProfileUrl = {
    Acquirente: '/ManagementAccount/applyChangeAcquirente',
    AgenteImmobiliare: '/ManagementAccount/applyChangeAgent',
    Amministratore: '/ManagementAccount/applyChangeAdmin'
  };

  static String? jwt;

  Connection._();

  static Future<bool> _initialize() async {
    try {
      final directory = await getApplicationDocumentsDirectory();
      final storage = File('${directory.path}/dietiEstate25.json');

      if (await storage.exists()) {
        String content = await storage.readAsString();
        dynamic json = jsonDecode(content);
        jwt = json['JWT']; // Assegna il JWT alla variabile statica
        logger.d("JWT letto dallo storage: $jwt");
        return true;
      } else {
        logger.d("JWT letto dallo storage: nessun file trovato");
        return false;
      }
    } catch (e) {
      logger.e("Errore durante l'inizializzazione: $e");
      return false;
    }
  }

  static Future<http.Response?> makePostRequest(
      Map<String, dynamic>? body, String? path) async {
    logger.d("JWT in POST request $jwt");
    try {
      String fullUrl = baseUrl;
      if (path != null && path.isNotEmpty) {
        fullUrl += path.startsWith('/') ? path : '/$path';
      }
      logger.d("POST REQUEST: ${fullUrl}");

      final response = await http.post(
        Uri.parse(fullUrl),
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer $jwt",
        },
        body: jsonEncode(body),
      );
      
      // Controllo se la richiesta ha avuto successo (200-299)
      if (response.statusCode >= 200 && response.statusCode < 300) {
        return response;
      } else {
        logger.e("Errore HTTP: ${response.statusCode} - ${response.body}");
        return null;
      }
    } catch (e) {
      logger.e("Errore durante la richiesta: $e");
      return null;
    }
  }

  static Future<http.Response?> makeGetRequest(String? path) async {
    logger.d("JWT in GET request $jwt");
    try {
      String fullUrl = baseUrl;
      if (path != null && path.isNotEmpty) {
        fullUrl += path.startsWith('/') ? path : '/$path';
      }
      logger.d("GET REQUEST: ${fullUrl}");
      logger.d('Il JWT e $jwt');
      // Fai la richiesta HTTP con il JWT nell'header
      final response = await http.get(
        Uri.parse(fullUrl),
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer $jwt", // Aggiunto il token Bearer
        },
      );

      // Controlla se la richiesta ha avuto successo (200-299)
      if (response.statusCode >= 200 && response.statusCode < 300) {
        return response;
      } else {
        logger.e("Errore HTTP: ${response.statusCode} - ${response.body} \n${fullUrl}");
        return null;
      }
    } catch (e) {
      logger.e("Errore durante la richiesta: $e");
      return null;
    }
  }

  static Future<http.Response?> makeLogin(Map<String, dynamic>? body) async {
    try {
      final response = await http.post(
        Uri.parse("$baseUrl/login"),
        headers: {
          "Content-Type": "application/json",
        },
        body: jsonEncode(body),
      );

      // Controlla se la richiesta ha avuto successo (200-299)
      if (response.statusCode >= 200 && response.statusCode < 300) {
        return response;
      } else {
        print("Errore HTTP: ${response.statusCode} - ${response.body}");
        return null;
      }
    } catch (e) {
      print("Errore durante la richiesta: $e");
      return null;
    }
  }

  static Future<http.Response?> validateJwtRequest() async {
    logger.d("Sto validando il JWT: $jwt tramite KONG");
    try {
      final response = await http.get(
        Uri.parse("$baseUrl/jwt"),
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer $jwt", // Aggiunto il token Bearer
        },
      );

      // Controlla se la richiesta ha avuto successo (200-299)
      if (response.statusCode >= 200 && response.statusCode < 300) {
        return response;
      } else {
        logger.e("Errore HTTP: ${response.statusCode} - ${response.body}");
        return null;
      }
    } catch (e) {
      logger.e("Errore durante la richiesta: $e");
      return null;
    }
  }

  static Future<http.Response?> validateNewJwtRequest(String newJwt) async {
    logger.d("JWT in JWT request $newJwt");

    try {
      final response = await http.get(
        Uri.parse("$baseUrl/jwt"),
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer $newJwt", // Aggiunto il token Bearer
        },
      );

      // Controlla se la richiesta ha avuto successo (200-299)
      if (response.statusCode >= 200 && response.statusCode < 300) {
        return response;
      } else {
        logger.e("Errore HTTP: ${response.statusCode} - ${response.body}");
        return null;
      }
    } catch (e) {
      logger.e("Errore durante la richiesta: $e");
      return null;
    }
  }

  static Future<bool> init() async {
    return _initialize(); // Attende che l'inizializzazione statica sia completata
  }
}
