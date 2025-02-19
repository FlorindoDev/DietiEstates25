class Utente {
  int _id_user = 0;
  String _email = "";
  String _cognome = "";
  String _password = "";
  String _idPushNotify = "";
  String _nome = "";

  Utente._builder(UtenteBuilder builder)
      : _id_user = builder._id_user,
        _nome = builder._nome ?? '',
        _cognome = builder._cognome ?? '',
        _email = builder._email,
        _idPushNotify = builder._idPushNotify,
        _password = builder._password ?? '';

  static UtenteBuilder get builder => UtenteBuilder();

  
  int get id_user => _id_user;

  set id_user(int value) {
    _id_user = value;
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



}


class UtenteBuilder{

  int _id_user = 0;
  String _email = "";
  String _cognome = "";
  String _password = "";
  String _idPushNotify = "";
  String _nome = "";

  UtenteBuilder setId(int id_user) {
    this._id_user = id_user;
    return this;
  }

  UtenteBuilder setName(String nome) {
    this._nome = nome;
    return this;
  }

  UtenteBuilder setEmail(String email) {
    this._email = email;
    return this;
  }

  UtenteBuilder setPassword(String password) {
    this._password = password;
    return this;
  }

  UtenteBuilder setNotify(String idPushNotify) {
    this._idPushNotify = idPushNotify;
    return this;
  }

  Utente build() {
    if (_email == null) {
      throw Exception("email are required");
    }
    return Utente._builder(this);
  }
    
  }