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
    // 1) Controllo formato: esattamente 11 cifre
    // if (!RegExp(r'^\d{11}$').hasMatch(partialVA)) {
    //   throw Exception('La Partita IVA deve contenere esattamente 11 cifre numeriche.');
    // }

    // // 2) Estrazione delle cifre
    // List<int> digits = partialVA.split('').map(int.parse).toList();

    // // 3) Calcolo del checksum
    // int sum = 0;
    // for (int i = 0; i < 10; i++) {
    //   int d = digits[i];
    //   if (i % 2 == 0) {
    //     // posizioni dispari (1ª, 3ª, …) - indice 0,2,4…
    //     sum += d;
    //   } else {
    //     // posizioni pari (2ª, 4ª, …) - indice 1,3,5…
    //     int doubled = d * 2;
    //     sum += (doubled > 9) ? doubled - 9 : doubled;
    //   }
    // }

    // // 4) Ricavo la cifra di controllo attesa
    // int expectedCheck = (10 - (sum % 10)) % 10;

    // // 5) Confronto con l'ultima cifra
    // if (digits[10] != expectedCheck) {
    //   throw Exception('Checksum non valido per la Partita IVA fornita.');
    // }

    // Se siamo qui, è valido
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
      throw Exception("Nome non valido");
    }

    return true;
    
  }

  @override
  bool validateSurname(String surname) {
    if(!_isValidName(surname)){
      throw Exception("Cognome non valido");
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