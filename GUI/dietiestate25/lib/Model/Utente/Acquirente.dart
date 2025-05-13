part of './Utente.dart';

class Acquirente extends Utente {
  bool? notify_appointment;
  bool? notify_new_estate;
  bool? change_price_notify;

  Acquirente._builder(AcquirenteBuilder builder) : super._builder(builder) {
    notify_appointment = builder._notify_appointment;
    notify_new_estate = builder._notify_new_estate;
    change_price_notify = builder._change_price_notify;
  }

  factory Acquirente.fromJson(Map<String, dynamic> json) {
    return Acquirente.builder
        .setId(json['idUser']?.toString() ?? '')
        .setName(json['nome'] as String? ?? '')
        .setEmail(json['email'] as String? ?? '')
        .setCognome(json['cognome'] as String? ?? '')
        .setPassword(json['password'] as String? ?? '')
        .setNotifyAppointm(json['notifyAppointment'] as bool? ?? false)
        .build();
  }

  static AcquirenteBuilder get builder => AcquirenteBuilder();

  @override
  Map<String, dynamic> toJson() {
    return {
      ...super.toJson(),
      // 'idacquirente': idacquirente,
      'notify_appointment': notify_appointment,
      'notify_new_estate': notify_new_estate,
      'change_price_notify': change_price_notify,
    };
  }
}

class AcquirenteBuilder extends UtenteBuilder {
  bool? _notify_appointment;
  bool? _notify_new_estate;
  bool? _change_price_notify;

  @override
  AcquirenteBuilder setId(String idUser) {
    super.setId(idUser);
    return this;
  }

  @override
  AcquirenteBuilder setName(String nome) {
    super.setName(nome);
    return this;
  }

  @override
  AcquirenteBuilder setCognome(String cognome) {
    super.setCognome(cognome);
    return this;
  }

  @override
  AcquirenteBuilder setEmail(String email) {
    super.setEmail(email);
    return this;
  }

  @override
  AcquirenteBuilder setPassword(String password) {
    super.setPassword(password);
    return this;
  }

  AcquirenteBuilder setNotifyAppointm(bool value) {
    _notify_appointment = value;
    return this;
  }

  AcquirenteBuilder setNotifyNewEsta(bool value) {
    _notify_new_estate = value;
    return this;
  }

  AcquirenteBuilder setChangePriceN(bool value) {
    _change_price_notify = value;
    return this;
  }

  @override
  Acquirente build() {
    return Acquirente._builder(this);
  }

}