import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/Home/HomeWindow.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

class RouteWindows {
  static const loginWindow = '/LoginWindow';
  static const singUpWindow = '/SingUpWindow';
  static const createAgencyWindow = '/CreateAgencyWindow';
  static const homeWindow = '/HomeWindow';

  static Future<String> checkLogin() async {
    bool isLoggedIn = await AccessController.checkLogin();
    return isLoggedIn ? homeWindow : loginWindow;
  }

  static Route<dynamic> generateRoute(RouteSettings settings) {
    print("[settings name] ${settings.name}");

    if (settings.name != '/') {
      switch (settings.name) {
        case RouteWindows.loginWindow:
          return AccessController.goToLoginWindow();

        case RouteWindows.singUpWindow:
          return AccessController.goToSignUpWindow();

        case RouteWindows.createAgencyWindow:
          return AccessController.goToCreateAgencyWindow();

        case RouteWindows.homeWindow:
          return MaterialPageRoute(builder: (_) => HomeWindow(appbar: MyApp.appBarNotBackable));
      }
    } else {
      print("[d] not initilaized route");
    }

    return MaterialPageRoute(builder: (_) => LoginWindow(appbar: MyApp.appBarNotBackable));
  }
}
