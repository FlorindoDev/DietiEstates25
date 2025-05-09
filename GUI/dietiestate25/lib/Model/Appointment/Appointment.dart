import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

class Appointment {
  int idAppointment;
  String data;
  String esito;
  String dataRichesta;
  int idAcquirente;
  int idEstate;

  Appointment({
    required this.idAppointment,
    required this.data,
    required this.esito,
    required this.dataRichesta,
    required this.idAcquirente,
    required this.idEstate,
  });

  Map<String, dynamic> toJson(String esito) {
    return {
      'idAppointment': idAppointment,
      'data': data,
      'esito': esito,
      'dataRichesta': dataRichesta,
      'acquirente': {'idUser': idAcquirente},
      'estate': {'idEstate': idEstate},
    };
  }

  factory Appointment.fromJson(Map<String, dynamic> json) {
    return Appointment(
      idAppointment: json['idAppointment'],
      data: json['data'],
      esito: json['esito'],
      dataRichesta: json['dataRichesta'],
      idAcquirente: json['acquirente']['idUser'],
      idEstate: json['estate']['idEstate'],
    );
  }

  CircleAvatar icon() {
    return CircleAvatar(
        radius: 16, backgroundColor: MyApp.rosso, child: Icon(Icons.pending));
  }
}
