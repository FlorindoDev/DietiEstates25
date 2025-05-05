part of './Utente.dart';

class Acquirente extends Utente {
  // int? idacquirente;
  bool? notify_appointment;
  bool? notify_new_estate;
  bool? change_price_notify;

  Acquirente._builder(AcquirenteBuilder builder) : super._builder(builder) {
    // idacquirente = builder._idacquirente;
    notify_appointment = builder._notify_appointment;
    notify_new_estate = builder._notify_new_estate;
    change_price_notify = builder._change_price_notify;
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
  // int? _idacquirente;
  bool? _notify_appointment;
  bool? _notify_new_estate;
  bool? _change_price_notify;

  // AcquirenteBuilder setIdacquirente(int id) {
  //   _idacquirente = id;
  //   return this;
  // }

  AcquirenteBuilder setNotifyAppointr(bool value) {
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

  @override
  AcquirenteBuilder setId(String idUser) {
    super.setId(idUser);
    return this;
  }

}