import 'package:dietiestate25/Model/Notify/Notify.dart';

class AppointmentPending extends Notify {
  AppointmentPending({
    required int idNotify,
    required String data,
    required String dataRicezione,
    required String message,
    required String tipo,
    required int idEstate,
    required int idAcquirente,
  }) : super(
          idNotify: idNotify,
          data: data,
          dataRicezione: dataRicezione,
          message: message,
          tipo: tipo,
          idEstate: idEstate,
          idAcquirente: idAcquirente,
        );

  // Factory constructor per creare un'istanza di Appoitmentaccepted da JSON
  factory AppointmentPending.fromJson(Map<String, dynamic> json) {
    return AppointmentPending(
      idNotify: json['idNotify'],
      data: json['data'],
      dataRicezione: json['dataRicezione'],
      message: json['message'],
      tipo: json['tipo'],
      idEstate: json['estate']['idEstate'],
      idAcquirente: json['acquirente']['idUser'],
    );
  }

  bool isRifiutato() {
    return false;
  }
}
