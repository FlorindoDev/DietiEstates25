import 'dart:convert';
import 'package:dietiestate25/Connection/Connection.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentNotification.dart';
import 'package:dietiestate25/Model/Notify/AppointmentPending.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/Validation/Validetor.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:http/http.dart' as http;
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Model/Notify/Notify.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentAccept.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentPending.dart'
    as Model;
import 'package:dietiestate25/Model/Appointment/AppointmentReject.dart';
import 'package:dietiestate25/Model/Agenzia/Agenzia.dart';
import 'package:dietiestate25/Model/Estate/Indirizzo.dart';

class AgentHomeController {
  //static final String urlEstates = 'http://127.0.0.1:7004/api/'; // Per Windows
  static final String urlEstates = 'http://10.0.2.2:7004/api/'; // Per Android

  //static final String urlNotify = 'http://127.0.0.1:7008/api/notifies/agent'; // Per Windows
  static final String urlNotify =
      'http://10.0.2.2:7008/api/notifies/agent'; // Per Android

  //static final String urlAppointment = 'http://127.0.0.1:7006/api/appointments/agent'; // Per Windows
  static final String urlAppointment =
      'http://10.0.2.2:7006/api/appointments/agent'; // Per Android

  static final String urlAppointmentSpecific = '/api/appointments';

  //static final String urlAppointmentUpdate = 'http://127.0.0.1:7006/api/appointments'; // Per Windows
  static final String urlAppointmentUpdate =
      'http://10.0.2.2:7006/api/appointments'; // Per Android

  static final String urlAgenti = 'ManagementAgent';

  static Validator valida = Validate();

  // static Utente utente = MyApp.user;
  static Utente utente = loggedUser;

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
    logger.d("[getNotify] ${utente.toJson()}");

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
    AppointmentNotification Appointments = new AppointmentNotification(
        idAppointment: 0,
        data: "0",
        esito: "0",
        dataRichesta: "0",
        idAcquirente: 0,
        idEstate: 0,
        nomeEcognome: "0",
        viaEstate: "0");

    // Uri uri = Uri.parse(
    //     urlAppointmentSpecific + '?id=' + idAppointment + '&extended=true');
    http.Response? response = await Connection.makeGetRequest(
        urlAppointmentSpecific + '?id=' + idAppointment + '&extended=true');

    // try {
    //   // print(uri);
    //   // Fai la richiesta HTTP
    //   response = await http.get(
    //     uri, // URL valido
    //     headers: {"Content-Type": "application/json"},
    //   ); //as http.Response;
    // } catch (e) {
    //   return Appointments;
    // }

    // Controlla se lo statusCode non è 200 (OK)
    dynamic ris;

    if (response != null) {
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
    }
    return Appointments;
  }

  static Future<bool> updateAppointment(
      dynamic context, bool accettato, Appointment appointment) async {
    String uri;
    Map<String, dynamic>? body;
    if (accettato == true) {
      uri = urlAppointmentSpecific + "/acceptAppointment";
      body = appointment.toJson("Accettato");
    } else {
      uri = urlAppointmentSpecific + "/declineAppointment";
      body = appointment.toJson("Rifiutato");
    }

    bool esito = false;
    print(uri);
    print(body);

    http.Response? response = await Connection.makePostRequest(body, uri);
    if (response == null) {
      MyApp.mostraPopUpWarining(
          context, "Errore", "Qualcosa è adato storto durante l'operazione");
      return esito;
    }
    // try {
    //   print(uri);
    //   // Fai la richiesta HTTP
    //   response = await http.post(
    //     uri,
    //     headers: {
    //       "Content-Type": "application/json",
    //       // Aggiungi altri header se necessario (es: token auth)
    //     },
    //     body: body, // Codifica l'oggetto in JSON
    //   ); //as http.Response;
    // } catch (e) {
    //   MyApp.mostraPopUpWarining(
    //       context, "Errore", "Qualcosa è adato storto durante l'operazione");
    //   return esito;
    // }

    dynamic ris;

    try {
      ris = json.decode(utf8.decode(response.bodyBytes));
      print("DBUG RIS: ${ris['code']}");
      if (ris['code'] == 0) {
        print("DEBUG in ris code 0");
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

  static Future<List<AgenteImmobiliare>> getAgenti(
      dynamic context, String quary) async {
    http.Response? response = await Connection.makeGetRequest(urlAgenti +
        '/getAgents?codicePartitaIVA=' +
        (loggedUser.partitaiva ?? "0") +
        quary);
    if (response == null) {
      return List<AgenteImmobiliare>.empty();
    } else {}

    dynamic ris;
    List<AgenteImmobiliare> agenti = [];
    try {
      // if (response != null) {
      ris = json.decode(utf8.decode(response.bodyBytes));
      if (ris['code'] == 0) {
        for (int i = 0; i < ris['agents'].length; i++) {
          AgenteImmobiliare agente =
              AgenteImmobiliare.fromJson(ris['agents'][i]);

          agenti.add(agente);
        }
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", ris['message']);
      }
      // }
    } catch (e) {
      return List<AgenteImmobiliare>.empty();
    }
    return agenti;
  }

  static removeAgent(
      BuildContext context, AgenteImmobiliare agente, String quary) async {
    http.Response? response = await Connection.makeDeleteRequest(
        urlAgenti + '/removeAgent?email=${agente.email}' + quary);

    if (response == null) {
      MyApp.mostraPopUpWarining(context, "Errore", "Qualcosa è andato storto");
      return;
    } else {
      manageResponse(response, context);
    }
  }

  static void manageResponse(http.Response response, BuildContext context) {
    var responseData = json.decode(utf8.decode(response.bodyBytes));
    if (response.statusCode == 200) {
      // La richiesta è andata a buon fine

      if (responseData['code'] == 0) {
        MyApp.mostraPopUpSuccess(context, "Successo", responseData['message']);
      } else {
        MyApp.mostraPopUpWarining(context, "Errore", responseData['message']);
      }
    } else {
      // La richiesta non è andata a buon fine
      MyApp.mostraPopUpWarining(context, "Errore", responseData['message']);
    }
  }

  static void createAgent(
      BuildContext context, AgenteImmobiliare agent, String s) async {
    try {
      valida.validateEmail(agent.email);
      valida.validatePassword(agent.password);
      valida.validateName(agent.nome);
      valida.validateSurname(agent.cognome);
    } catch (e) {
      MyApp.mostraPopUpWarining(context, "Errore", e.toString());
      return;
    }

    final Map<String, dynamic> userMap = agent.toJson();
    http.Response? response =
        await Connection.makePostRequest(userMap, urlAgenti + '/addAgent');
    if (response == null) {
      MyApp.mostraPopUpWarining(context, "Errore", "Errore di rete");
      return;
    } else {
      manageResponse(response, context);
    }
  }

  static Future<void> createEstate({
    // Estate
    required String descrizione,
    required double price,
    required double space,
    required int rooms,
    required int wc,
    required int floor,
    required int garage,
    required bool elevator,
    required String stato,
    required String mode,
    required String classeEnergetica,
    required List<String> foto,
    required dynamic context,

    // Indirizzo
    required String statoIndirizzo,
    required String citta,
    String? quartiere,
    required String via,
    required String numeroCivico,
    required String cap,
    required double latitudine,
    required double logitudine,
  }) async {
    final AgenteImmobiliare agente = loggedUser;

    final agenzia =
        Agenzia.builder.setPartitaIVA(agente.partitaiva as String).build();

    final indirizzo = Indirizzo(
      idIndirizzo: 0,
      stato: stato,
      citta: citta,
      quartiere: quartiere,
      via: via,
      numeroCivico: numeroCivico,
      cap: int.parse(cap),
      latitudine: latitudine,
      logitudine: logitudine,
    );

    final estate = EstateBuilder(0)
        .setAgenziaBuilder(agenzia)
        .setAgenteBuilder(agente)
        .setIndirizzoBuilder(indirizzo)
        .setFotoBuilder(foto)
        .setDescrizioneBuilder(descrizione)
        .setPriceBuilder(price)
        .setSpaceBuilder(space)
        .setRoomsBuilder(rooms)
        .setFloorBuilder(floor)
        .setWcBuilder(wc)
        .setGarageBuilder(garage)
        .setElevatorBuilder(elevator)
        .setClasseEnergeticaBuilder(classeEnergetica)
        .setModeBuilder(mode)
        .setStatoBuilder(stato)
        .build();

    final body = estate.toJson();
    print(body);

    http.Response? response =
        await Connection.makePostRequest(body, 'AdsEstate/createEstate');

    if (response!.statusCode == 200 || response.statusCode == 201) {
      print("Estate creata con successo.");
      print(response.body);
      MyApp.mostraPopUpSuccess(
          context, "Successo creazione", "successo aggiunta estate");
    } else {
      MyApp.mostraPopUpWarining(context, "Quanlcoda è andato storto", " ");
      print(response.body);
    }
  }
}
