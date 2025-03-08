import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

class AppointmentReject extends Appointment {
  AppointmentReject({
    required int idAppointment,
    required String data,
    required String esito,
    required String dataRichesta,
    required int idacquirente,
    required int idestate,
  }) : super(
          idAppointment: idAppointment,
          data: data,
          esito: esito,
          dataRichesta: dataRichesta,
          idAcquirente: idacquirente,
          idEstate: idestate,
        );

  factory AppointmentReject.fromJson(Map<String, dynamic> json) {
    return AppointmentReject(
      idAppointment: json['idAppointment'],
      data: json['data'],
      esito: json['esito'],
      dataRichesta: json['dataRichesta'],
      idacquirente: json['acquirente']['idUser'],
      idestate: json['estate']['idEstate'],
    );
  }

  CircleAvatar icon() {
    return CircleAvatar(
      radius: 16,
      backgroundColor: MyApp.rosso,
      child: Icon(
        Icons.close,
        color: Color.fromARGB(255, 0, 0, 0),
        size: 25,
      ),
    );
  }
}
