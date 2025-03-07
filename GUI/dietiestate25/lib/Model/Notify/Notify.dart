class Notify {
  late int idNotify;

  late String data;

  late String dataRicezione;

  late String message;

  late String tipo;

  late int idEstate;

  late int idAcquirente;

  Notify({required this.idNotify, required this.data, required this.dataRicezione, required this.message, required this.tipo, required this.idEstate, required this.idAcquirente});

  factory Notify.fromJson(Map<String, dynamic> json) {
    return Notify(idNotify: json['idNotify'], data: json['data'], dataRicezione: json['dataRicezione'], message: json['message'], tipo: json['tipo'], idEstate: json['estate']['idEstate'], idAcquirente: json['acquirente']['idUser']);
  }

  bool isRifiutato() {
    return true;
  }
}
