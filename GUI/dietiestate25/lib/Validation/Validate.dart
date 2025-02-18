import 'Validetor.dart';

class Validate implements Validator {
  
  //Costruttore privato
  Validate._internal();
  
  //Campo statico che contiene l'istanza unica
  static final Validate _instance = Validate._internal();
  
  //Metodo/factory per accedere all'istanza
  factory Validate() {
    return _instance;
  }

  @override
  bool validateEmail(String email) {
    if(!_isValidEmail(email)){
      throw Exception("Email non valida");
    }

    return true;
  }

  @override
  bool validatePassword(String password) {
    if(password.length < 8){
      throw Exception("Password non valida, minimo 8 caratteri");
    }
    return true;
  }

  @override
  bool validateAgencyName(String agencyName) {
    // TODO: implementa la logica di validazione
    return true;
  }

  @override
  bool validatePartitalVA(String partialVA) {
    // TODO: implementa la logica di validazione
    return true;
  }

  @override
  bool validateSede(String sede) {
    // TODO: implementa la logica di validazione
    return true;
  }

  @override
  bool validateName(String name) {
    if(!_isValidName(name)){
      throw Exception("Nome non valida");
    }

    return true;
    
  }

  @override
  bool validateSurname(String surname) {
    if(!_isValidName(surname)){
      throw Exception("Cognome non valida");
    }
    return true;
  }

  @override
  bool validateDate(DateTime start, DateTime end) {
    // TODO: implementa la logica di validazione
    return true;
  }

  bool _isValidEmail(String email) {
  
    final RegExp emailRegex = RegExp(
      r'^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+\.[a-zA-Z]{2,}$',
    );
    
    return emailRegex.hasMatch(email);
  }

  bool _isValidName(String nome) {
  
    final RegExp nomeRegex = RegExp(r"^[A-ZÀ-ÖØ-Ý][a-zà-öø-ý]+(?: [A-ZÀ-ÖØ-Ý][a-zà-öø-ý]+)*$");
    
    return nomeRegex.hasMatch(nome);
  }




}