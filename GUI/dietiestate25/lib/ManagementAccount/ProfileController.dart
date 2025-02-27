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