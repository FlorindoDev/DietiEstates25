import 'dart:convert';
import 'dart:io';
import 'package:path_provider/path_provider.dart';

class ProfileController{
  
  static Future<void> resetJWT() async {

    final directory = await getApplicationDocumentsDirectory();
    final file = File('${directory.path}/dietiEstate25.json');
    if (!(await file.exists())) {
      String content = await file.readAsString();
      dynamic json = jsonDecode(content);
      json['JWT'] = "";
      await file.writeAsString(jsonEncode(json));

    }else{}
      await file.writeAsString('{ "JWT":"" }');
    
  }

  
  


}

// Gli utenti devono poter modificare i loro dati personali: nome, cognome, e-mail, password. Gli 
// agenti immobiliari devono avere un profilo più dettagliato, con informazioni professionali (e.g.: una 
// breve biografia) e magari una valutazione lasciata dai clienti. È gradita la possibilità di aggiungere 
// anche delle foto per gli agenti immobiliari, per rendere i profili più “umani”. 