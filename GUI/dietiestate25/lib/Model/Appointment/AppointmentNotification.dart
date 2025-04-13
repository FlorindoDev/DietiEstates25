import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

class AppointmentNotification {
  int idAppointment;
  String data;
  String esito;
  String dataRichesta;
  int idAcquirente;
  int idEstate;

  AppointmentNotification({
    required this.idAppointment,
    required this.data,
    required this.esito,
    required this.dataRichesta,
    required this.idAcquirente,
    required this.idEstate,
  });

  factory AppointmentNotification.fromJson(Map<String, dynamic> json) {
    return AppointmentNotification(
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
