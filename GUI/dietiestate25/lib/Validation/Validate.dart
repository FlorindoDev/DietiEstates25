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

  bool validateEmail(String email) {
    if(!_isValidEmail(email)){
      throw Exception("Email non valida");
    }

    return true;
  }

  bool validatePassword(String password) {
    if(password.length < 8){
      throw Exception("Password non valida, minimo 8 caratteri");
    }
    return true;
  }

  bool validateAgencyName(String agencyName) {
    // TODO: implementa la logica di validazione
    return true;
  }

  bool validatePartitalVA(String partialVA) {
    // TODO: implementa la logica di validazione
    return true;
  }

  bool validateSede(String sede) {
    // TODO: implementa la logica di validazione
    return true;
  }

  bool validateName(String name) {
    // TODO: implementa la logica di validazione
    return true;
  }

  bool validateSurname(String surname) {
    // TODO: implementa la logica di validazione
    return true;
  }

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




}