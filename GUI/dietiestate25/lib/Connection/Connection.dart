import 'package:http/http.dart' as http;
import 'package:path_provider/path_provider.dart';
import 'dart:io';
import 'dart:convert';

class Connection {
  // static final Uri url = Uri.parse('http://localhost:8000/'); // KONG URL
  static final String baseUrl = 'http://10.0.2.2:8000';

  static String? jwt;
  // final String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYWZmYUByYWZmYS5jb20iLCJraWQiOiJ1c2VyIiwiaWF0IjoxNzE0NjA2NDcsImV4cCI6MTc0MTU0NzA0N30.GCfttSBjYk_00vVihEk0iR-vPO7tVCflfILEamSZinY5t8fNOWPBXVl6vbsWz1agyoveBHKqk_bzDBUHsAP_vg";

  Connection._();

  static Future<bool> _initialize() async {
    try {
      final directory = await getApplicationDocumentsDirectory();
      final storage = File('${directory.path}/dietiEstate25.json');

      if (await storage.exists()) {
        String content = await storage.readAsString();
        dynamic json = jsonDecode(content);
        jwt = json['JWT']; // Assegna il JWT alla variabile statica
        print("JWT letto dallo storage: $jwt");
        return true;
      } else {
        print("JWT letto dallo storage: nessun file trovato");
        return false;
      }
    } catch (e) {
      print("Errore durante l'inizializzazione: $e");
      return false;
    }
  }

  static Future<http.Response?> makePostRequest(
      Map<String, dynamic>? body, String? path) async {
    print("JWT in POST request $jwt");
    try {
      String fullUrl = baseUrl;
      if (path != null && path.isNotEmpty) {
        fullUrl += path.startsWith('/') ? path : '/$path';
      }
      // Fai la richiesta HTTP con il JWT nell'header
      final response = await http.post(
        Uri.parse(fullUrl),
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer $jwt", // Aggiunto il token Bearer
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

  static Future<http.Response?> makeGetRequest(String? path) async {
    print("JWT in GET request $jwt");
    try {
      String fullUrl = baseUrl;
      if (path != null && path.isNotEmpty) {
        fullUrl += path.startsWith('/') ? path : '/$path';
      }
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
        print("Errore HTTP: ${response.statusCode} - ${response.body}");
        return null;
      }
    } catch (e) {
      print("Errore durante la richiesta: $e");
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
    print("STO VALIDANDO IL JWT $jwt mando a kong");
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
        print("Errore HTTP: ${response.statusCode} - ${response.body}");
        return null;
      }
    } catch (e) {
      print("Errore durante la richiesta: $e");
      return null;
    }
  }

  static Future<http.Response?> validateNewJwtRequest(String newJwt) async {
    print("JWT in JWT request $newJwt");

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
        print("Errore HTTP: ${response.statusCode} - ${response.body}");
        return null;
      }
    } catch (e) {
      print("Errore durante la richiesta: $e");
      return null;
    }
  }

  static Future<bool> init() async {
    print("init connection");
    return _initialize(); // Attende che l'inizializzazione statica sia completata
    // print("end init connection");
  }
}
