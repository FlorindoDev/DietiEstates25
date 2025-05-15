part of './Utente.dart';

class AgenteImmobiliare extends Utente {
  String? biografia;
  String? immagineprofile;
  String? partitaiva;
  String? sensitivity;
  bool? notifyAppointment;

  AgenteImmobiliare._builder(AgenteImmobiliareBuilder builder) : super._builder(builder) {
    biografia = builder._biografia;
    immagineprofile = builder._immagineprofile;
    partitaiva = builder._partitaiva;
    sensitivity = builder._sensitivity;
    notifyAppointment = builder._notifyAppointment;
  }

  factory AgenteImmobiliare.fromJson(Map<String, dynamic> json) {
    return AgenteImmobiliare.builder
        .setId(json['idUser']?.toString() ?? '')
        .setName(json['nome'] as String? ?? '')
        .setEmail(json['email'] as String? ?? '')
        .setCognome(json['cognome'] as String? ?? '')
        .setImmagineprofile(json['immagineprofilo'] as String? ?? '')
        .setPassword(json['password'] as String? ?? '')
        .setBiografia(json['biografia'] as String? ?? '')
        .setPartitaiva(json['agency']?['codicePartitaIVA'] as String? ?? '')
        .setNotifyAppointm(json['notifyAppointment'] as bool? ?? false)
        .build();
  }

  static AgenteImmobiliareBuilder get builder => AgenteImmobiliareBuilder();

  @override
  Map<String, dynamic> toJson() {
    return {
      ...super.toJson(),
      'biografia': biografia,
      'immagineprofilo': immagineprofile,
      'agency': {
        'codicePartitaIVA': partitaiva,
      },
      'sensitivity': sensitivity,
      'notifyAppointment': notifyAppointment,
    };
  }
}

class AgenteImmobiliareBuilder extends UtenteBuilder {
  String? _biografia;
  String? _immagineprofile;
  String? _partitaiva;
  String? _sensitivity;
  bool? _notifyAppointment;


  @override
  AgenteImmobiliareBuilder setId(String idUser) {
    super.setId(idUser);
    return this;
  }

  @override
  AgenteImmobiliareBuilder setName(String nome) {
    super.setName(nome);
    return this;
  }

  @override
  AgenteImmobiliareBuilder setCognome(String cognome) {
    super.setCognome(cognome);
    return this;
  }

  @override
  AgenteImmobiliareBuilder setEmail(String email) {
    super.setEmail(email);
    return this;
  }

  @override
  AgenteImmobiliareBuilder setPassword(String password) {
    super.setPassword(password);
    return this;
  }

  @override
  AgenteImmobiliareBuilder setNotify(String idPushNotify) {
    super.setNotify(idPushNotify);
    return this;
  }

  AgenteImmobiliareBuilder setBiografia(String value) {
    _biografia = value;
    return this;
  }

  AgenteImmobiliareBuilder setImmagineprofile(String value) {
    _immagineprofile = value;
    return this;
  }

  AgenteImmobiliareBuilder setPartitaiva(String value) {
    _partitaiva = value;
    return this;
  }

  AgenteImmobiliareBuilder setSensitivity(String value) {
    _sensitivity = value;
    return this;
  }

  AgenteImmobiliareBuilder setNotifyAppointm(bool value) {
    _notifyAppointment = value;
    return this;
  }

  @override
  AgenteImmobiliare build() {
    return AgenteImmobiliare._builder(this);
  }

}