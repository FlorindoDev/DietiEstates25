import 'package:dietiestate25/Connection/Connection.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/AccessClass/AccessController.dart';
// import 'package:dietiestate25/Home/HomeController.dart';
import 'package:dietiestate25/Home/HomeWindow.dart';
import 'package:dietiestate25/Connection/Connection.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized(); // Assicura l'inizializzazione di Flutter
  await Connection.init();
  String initialRoute = await RouteWindows.checkLogin();
  runApp(MyApp(initialRoute: initialRoute));
}

class MyApp extends StatelessWidget {
  final String initialRoute;

  static const String fontApp = 'Goldbill-XLBold';
  static const String titleApp = 'UninaEstate25';

  static const Color rosso = Color(0xffE63746);
  static const Color blu = Color(0xff1D3558);
  static const Color celeste = Color(0xff447A9C);
  static const Color panna = Color(0xfff6f6f6);

  static final TextStyle stile_testo_solo_nero = const TextStyle(
    color: Colors.black,
  );

  const MyApp({Key? key, required this.initialRoute}) : super(key: key);

  static AppBar appBarNotBackable = AppBar(
    centerTitle: true,
    toolbarHeight: 100,
    leadingWidth: 0,
    backgroundColor: Colors.white,
    automaticallyImplyLeading: false,
    title: Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Image.asset(
          'images/logo/logo_piccolo.jpg',
          height: 85,
          width: 85,
        ),
        Row(
          children: [
            Text(
              'UninaEstates',
              style: TextStyle(
                fontSize: 25,
                color: Colors.black,
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

  static void mostraPopUpInformativo(dynamic context, String titolo, String messaggio) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(titolo),
          icon: Icon(Icons.warning_rounded),
          content: Text(messaggio),
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

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: titleApp,
      initialRoute: initialRoute,
      onGenerateRoute: RouteWindows.generateRoute,
      //darkTheme: ThemeData.dark(),
      theme: ThemeData(
        //scaffoldBackgroundColor: const Color(0xff447A9C),
        //scaffoldBackgroundColor: const Color(0xfff1faee),
        scaffoldBackgroundColor: Colors.white,

        fontFamily: fontApp,

        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.white,
          primary: blu,
          secondary: rosso,
          tertiary: celeste,
        ),

        textTheme: TextTheme(
          bodyMedium: TextStyle(
            fontFamily: fontApp,
          ),
        ),

        useMaterial3: true,
      ),
      // home: LoginWindow(appbar: MyApp.appBarNotBackable),
      // home: HomeWindow(appbar: MyApp.appBarNotBackable),
    );
  }
}
