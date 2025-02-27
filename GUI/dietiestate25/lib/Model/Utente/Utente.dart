import 'package:dietiestate25/Json/JSONConvertable.dart';

import 'dart:convert';

class Utente implements Jsonconvertable {
  String _idUser = "";
  String _email = "";
  String _cognome = "";
  String _password = "";
  String _idPushNotify = "";
  String _nome = "";

  Utente._builder(UtenteBuilder builder)
      : _idUser = builder._idUser,
        _nome = builder._nome ?? '',
        _cognome = builder._cognome ?? '',
        _email = builder._email,
        _idPushNotify = builder._idPushNotify,
        _password = builder._password ?? '';

  static UtenteBuilder get builder => UtenteBuilder();

  String get idUser => _idUser;

  set idUser(String value) {
    _idUser = value;
  }

  String get cognome => _cognome;

  set cognome(String value) {
    _cognome = value;
  }

  String get password => _password;

  set password(String value) {
    _password = value;
  }

  String get idPushNotify => _idPushNotify;

  set idPushNotify(String value) {
    _idPushNotify = value;
  }

  String get nome => _nome;

  set nome(String value) {
    _nome = value;
  }

  String get email => _email;

  set email(String value) {
    _email = value;
  }

  @override
  Map<String, dynamic> toJson() {
    return {
      'idUser': _idUser,
      'nome': _nome,
      'email': _email,
      'cognome': _cognome,
      'password': _password,
      "notifyAppointment": null,
      'idPushNotify': _idPushNotify,
    };
  }
}

class UtenteBuilder {
  String _idUser = "";
  String _email = "";
  String _cognome = "";
  String _password = "";
  String _idPushNotify = "";
  String _nome = "";

  UtenteBuilder setId(String idUser) {
    _idUser = idUser;
    return this;
  }

  UtenteBuilder setName(String nome) {
    _nome = nome;
    return this;
  }

  UtenteBuilder setEmail(String email) {
    _email = email;
    return this;
  }

  UtenteBuilder setPassword(String password) {
    _password = password;
    return this;
  }

  UtenteBuilder setNotify(String idPushNotify) {
    _idPushNotify = idPushNotify;
    return this;
  }

  Utente build() {
    return Utente._builder(this);
  }
}
