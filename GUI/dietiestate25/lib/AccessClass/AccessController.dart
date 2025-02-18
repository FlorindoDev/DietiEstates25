import 'package:dietiestate25/AccessClass/CreateAgencyWindow.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/AccessClass/SingUpWindow.dart';

class AccessController {
  static const loginWindow = '/LoginWindow';
  static const singUpWindow = '/SingUpWindow';
  static const createAgencyWindow = '/CreateAgencyWindow';

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
                    Text('UninaEstates',
                      style: TextStyle(
                        fontSize: 25,
                      ),
                    ),
                    Text('25',
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

  static MaterialPageRoute<dynamic> goToSignUp() {
    return MaterialPageRoute(builder: (_) => SingUpWindow(appbar: AccessController.appBarNotBackable));
  }

  static MaterialPageRoute<dynamic> goToLogin() {
    return MaterialPageRoute(builder: (_) => LoginWindow(appbar: AccessController.appBarNotBackable));
  }

  static MaterialPageRoute<dynamic> goToCreateAgency() {
    return MaterialPageRoute(builder: (_) => CreateAgencyWindow(appbar: AccessController.appBarNotBackable));
  }


  static Route<dynamic> generateRoute(RouteSettings settings){
    switch (settings.name){
      case AccessController.loginWindow:
        return goToLogin();
      case AccessController.singUpWindow:
        return goToSignUp();
      case AccessController.createAgencyWindow:
        return goToCreateAgency();
    }
    
    return MaterialPageRoute(builder: (_) => LoginWindow(appbar: AccessController.appBarNotBackable));
  }




}