import 'package:dietiestate25/Admin/AdminHomeWindow.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/Search/EstateInfoWindow.dart';
import 'package:dietiestate25/Search/EstatesViewWindow.dart';
import 'package:dietiestate25/Search/SearchCityWindow.dart';
import 'package:dietiestate25/Search/SearchFilterWindow.dart';
import 'package:dietiestate25/Search/SearchHome.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/Home/HomeWindow.dart';
import 'package:dietiestate25/AgentHome/AgentHomeWindow.dart';
import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class RouteWindows {
  // No logged windows
  static const loginWindow = '/LoginWindow';
  static const singUpWindow = '/SingUpWindow';
  static const createAgencyWindow = '/CreateAgencyWindow';

  // Main Windows
  static const homeWindow = '/HomeWindow';
  static const agentHomeWindow = '/AgentHomeWindow';
  static const adminHomeWindow = '/AdminHomeWindow';

  static const searchCityWindow = '/SearchCityWindow';
  static const searchHomeWindow = '/SearchHomeWindow';
  static const searchFilterWindow = '/SearchFilterWindow';
  static const estatesViewWindow = '/EstatesViewWindow';
  static const estateInfoWindow = '/EstateInfoWindow';

  static String citta = "";
  static bool reset = false;

  static List<Estate> estates = List.empty();

  static Estate selectedEstate = Estate(idEstate: 0, descrizione: "", price: 0, space: 0, rooms: 0, floor: 0, wc: 0, garage: 0, elevator: false, agenzia: null, agente: null, stato: null, mode: null, classeEnergetica: "", indirizzo: null);

 

  

  static Future<String> checkLogin() async {
    String isLoggedIn = await AccessController.checkLogin();
    if (isLoggedIn != "false") {
      if (isLoggedIn == "HomeWindow") {
        return homeWindow;
      } else if (isLoggedIn == "AgentHomeWindow") {
        return agentHomeWindow;
      } else if (isLoggedIn == "AdminHomeWindow") {
        return adminHomeWindow;
      }
    } else {
      return loginWindow;
    }
    return loginWindow;
  }

  static Route<dynamic> generateRoute(RouteSettings settings) {
    logger.d("[Route Settings] ${settings.name}");

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
              builder: (_) => AgentHomeWindow(appbar: MyApp.smallAppBar));
        
        case RouteWindows.adminHomeWindow:
          return MaterialPageRoute(
              builder: (_) => AdminHomeWindow(appbar: MyApp.appBarNotBackable));

        case RouteWindows.searchCityWindow:
          return MaterialPageRoute(
              builder: (_) => SearchCityWindow(appbar: MyApp.smallAppBar));

        case RouteWindows.searchHomeWindow:
          return MaterialPageRoute(
              builder: (_) => SearchHomeWindow(appbar: MyApp.smallAppBar));
        
        case RouteWindows.searchFilterWindow:
          return MaterialPageRoute(
              builder: (_) => SearchFilterWindow(appbar: MyApp.smallAppBar, citta : citta));

        case RouteWindows.estatesViewWindow:
          return MaterialPageRoute(
              builder: (_) => EstatesViewWindow(appbar: MyApp.smallAppBar, estates : estates));
        
        
        case RouteWindows.estateInfoWindow:
          return MaterialPageRoute(
              builder: (_) => EstateInfoWindow(appbar: MyApp.smallAppBar, estate : selectedEstate));

      }
    } else {
      logger.d("[Route Settings] not initilaized route");
    }

    return MaterialPageRoute(
        builder: (_) => LoginWindow(appbar: MyApp.appBarNotBackable));
  }

  
}
