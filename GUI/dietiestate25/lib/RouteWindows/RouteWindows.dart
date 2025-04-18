import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/Home/HomeController.dart';
import 'package:dietiestate25/Home/HomeWindow.dart';
import 'package:dietiestate25/AgentHome/AgentHomeWindow.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

class RouteWindows {
  static const loginWindow = '/LoginWindow';
  static const singUpWindow = '/SingUpWindow';
  static const createAgencyWindow = '/CreateAgencyWindow';
  static const homeWindow = '/HomeWindow';
  static const agentHomeWindow = '/AgentHomeWindow';

  static Future<String> checkLogin() async {
    String isLoggedIn = await AccessController.checkLogin();
    if (isLoggedIn != "false") {
      if (isLoggedIn == "HomeWindow") {
        return homeWindow;
      } else if (isLoggedIn == "AgentHomeWindow") {
        return agentHomeWindow;
      }
    } else {
      return loginWindow;
    }
    return loginWindow;
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
          return MaterialPageRoute(
              builder: (_) => HomeWindow(appbar: MyApp.appBarNotBackable));
        case RouteWindows.agentHomeWindow:
          return MaterialPageRoute(
              builder: (_) => AgentHomeWindow(appbar: MyApp.appBarNotBackable));
      }
    } else {
      print("[d] not initilaized route");
    }

    return MaterialPageRoute(
        builder: (_) => LoginWindow(appbar: MyApp.appBarNotBackable));
  }
}
