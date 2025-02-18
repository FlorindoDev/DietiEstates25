import 'package:dietiestate25/AccessClass/CreateAgencyWindow.dart';
import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/Validation/Validetor.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/AccessClass/SingUpWindow.dart';

class AccessController {
  
  
  static const loginWindow = '/LoginWindow';
  static const singUpWindow = '/SingUpWindow';
  static const createAgencyWindow = '/CreateAgencyWindow';

  static String token = "";
  static Validator valida = Validate();
  

  static AppBar appBarNotBackable = AppBar(
    centerTitle: true,
    toolbarHeight: 120,
    leadingWidth: 0,
    automaticallyImplyLeading: false,
    title: Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Image.asset(
          'images/logo/logo_piccolo.jpg',
          height: 100,
          width: 100,
        ),
        Row(
          children: [
            Text(
              'UninaEstates',
              style: TextStyle(
                fontSize: 25,
              ),
            ),
            Text(
              '25',
              style: TextStyle(
                color: MyApp.rosso,
                fontSize: 25,
              ),
            )
          ],
        ),
      ],
    ),
  );

  static final ButtonStyle clickable_style_button = ButtonStyle(
    backgroundColor: WidgetStateProperty.all(MyApp.blu),
    foregroundColor: WidgetStateProperty.all(Colors.white),
  );

  static final ButtonStyle not_clickable_style_button = ButtonStyle(
    backgroundColor: WidgetStateProperty.all(MyApp.celeste),
    foregroundColor: WidgetStateProperty.all(Colors.white),
  );



  static void mostraPopUp(dynamic context, String titolo, String messaggio){
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(titolo),
          icon: Icon(Icons.warning_rounded),
          content: Text(messaggio),
          contentTextStyle: TextStyle(

          ),
          actions: <Widget>[
            TextButton(
              child: Text("OK"),
              onPressed: () {
                Navigator.of(context).pop(); // Chiude il dialogo
              },
            ),
          ],
        );
      },
    );

  }

  
  static void toLogin(String email, String password){
    
    valida.validateEmail(email);
    valida.validatePassword(password);

  }

  static void toSignUp(String email, String password){
    valida.validateEmail(email);
    valida.validatePassword(password);
    
  }

  static void toCreateAgency(String email, String nome_agenzia, String sede, String partitaIVA){
    valida.validateEmail(email);
    valida.validateSede(sede);
    valida.validatePartitalVA(partitaIVA);
   

    
  }





  static MaterialPageRoute<dynamic> goToSignUpWindow() {
    return MaterialPageRoute(builder: (_) => SingUpWindow(appbar: AccessController.appBarNotBackable));
  }

  static MaterialPageRoute<dynamic> goToLoginWindow() {
    return MaterialPageRoute(builder: (_) => LoginWindow(appbar: AccessController.appBarNotBackable));
  }

  static MaterialPageRoute<dynamic> goToCreateAgencyWindow() {
    return MaterialPageRoute(builder: (_) => CreateAgencyWindow(appbar: AccessController.appBarNotBackable));
  }




  static Route<dynamic> generateRoute(RouteSettings settings){
    switch (settings.name){
      case AccessController.loginWindow:
        return goToLoginWindow();
      case AccessController.singUpWindow:
        return goToSignUpWindow();
      case AccessController.createAgencyWindow:
        return goToCreateAgencyWindow();
    }

    return MaterialPageRoute(builder: (_) => LoginWindow(appbar: AccessController.appBarNotBackable));
  }
}
