part of './Utente.dart';

class Amministratore extends Utente {
  bool? issupportoammi;
  String? partitaiva;

  Amministratore._builder(AmministratoreBuilder builder)
      : super._builder(builder) {
    issupportoammi = builder._issupportoammi;
    partitaiva = builder._partitaiva;
  }

 
  factory Amministratore.fromJson(Map<String, dynamic> json) {
    return Amministratore.builder
        .setId(json['idUser']?.toString() ?? '')
        .setName(json['nome'] as String? ?? '')
        .setEmail(json['email'] as String? ?? '')
        .setCognome(json['cognome'] as String? ?? '')
        .setPassword(json['password'] as String? ?? '')
        .setPartitaiva(json['agency']?['codicePartitaIVA'] as String? ?? '')
        .setIssupportoammi(json['support'])
        // .setNotify(json['idPushNotify'])
        // .setNotifyAppointm(json['notifyAppointment'] as bool? ?? false)
        .build();
  }


  static AmministratoreBuilder get builder => AmministratoreBuilder();

  @override
  Map<String, dynamic> toJson() {
    // campi base
    final Map<String, dynamic> base = super.toJson();

    // Rimuovo i campi che non servono per l'admin
    base.remove('notifyAppointment');
    base.remove('idPushNotify');
    base.remove('notifyNewEstate');
    base.remove('changePriceNotify');

    // Aggiungi i campi specifici di Amministratore
    base.addAll({
      'support': issupportoammi,
      'agency': {
        'codicePartitaIVA': partitaiva,
      },
    });

    return base;
  }


}

class AmministratoreBuilder extends UtenteBuilder {
  bool? _issupportoammi;
  String? _partitaiva;

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
  AmministratoreBuilder setCognome(String cognome) {
    super.setCognome(cognome);
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
