part of './Utente.dart';

class Amministratore extends Utente {
  // int? idamministrator;
  bool? issupportoammi;
  String? partitaiva;

  Amministratore._builder(AmministratoreBuilder builder)
      : super._builder(builder) {
    // idamministrator = builder._idamministrator;
    issupportoammi = builder._issupportoammi;
    partitaiva = builder._partitaiva;
  }

  static AmministratoreBuilder get builder => AmministratoreBuilder();

  @override
  Map<String, dynamic> toJson() {
    return {
      ...super.toJson(),
      // 'idamministrator': idamministrator,
      'issupportoammi': issupportoammi,
      'partitaiva': partitaiva,
    };
  }
}

class AmministratoreBuilder extends UtenteBuilder {
  // int? _idamministrator;
  bool? _issupportoammi;
  String? _partitaiva;

  // AmministratoreBuilder setIdamministrator(int id) {
  //   _idamministrator = id;
  //   return this;
  // }

  AmministratoreBuilder setIssupportoammi(bool value) {
    _issupportoammi = value;
    return this;
  }

  AmministratoreBuilder setPartitaiva(String value) {
    _partitaiva = value;
    return this;
  }

  @override
  Amministratore build() {
    return Amministratore._builder(this);
  }

  // Override parent methods to return AmministratoreBuilder
  @override
  AmministratoreBuilder setId(String idUser) {
    super.setId(idUser);
    return this;
  }

  @override
  AmministratoreBuilder setName(String nome) {
    super.setName(nome);
    return this;
  }

  @override
  AmministratoreBuilder setEmail(String email) {
    super.setEmail(email);
    return this;
  }

  @override
  AmministratoreBuilder setPassword(String password) {
    super.setPassword(password);
    return this;
  }

  @override
  AmministratoreBuilder setNotify(String idPushNotify) {
    super.setNotify(idPushNotify);
    return this;
  }
}
