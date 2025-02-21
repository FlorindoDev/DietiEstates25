import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';


class RouteWindows {

  static const loginWindow = '/LoginWindow';
  static const singUpWindow = '/SingUpWindow';
  static const createAgencyWindow = '/CreateAgencyWindow';
  static const homeWindow = '/HomeWindow';

  static Route<dynamic> generateRoute(RouteSettings settings) {
    switch (settings.name) {
      case RouteWindows.loginWindow:
        return AccessController.goToLoginWindow();
      case RouteWindows.singUpWindow:
        return AccessController.goToSignUpWindow();
      case RouteWindows.createAgencyWindow:
        return AccessController.goToCreateAgencyWindow();
    }

    return MaterialPageRoute(builder: (_) => LoginWindow(appbar: MyApp.appBarNotBackable));
  }

}