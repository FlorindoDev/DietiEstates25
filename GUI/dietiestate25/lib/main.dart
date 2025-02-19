import 'package:flutter/material.dart';
import 'package:dietiestate25/AccessClass/LoginWindow.dart';
import 'package:dietiestate25/AccessClass/AccessController.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static const String fontApp = 'Goldbill-XLBold';
  static const String titleApp = 'UninaEstate25';
  
  static const Color rosso = Color(0xffE63746); 
  static const Color blu = Color(0xff1D3558);
  static const Color celeste = Color(0xff447A9C);
  static const Color panna = Color(0xfff1faee);

  static final TextStyle stile_testo_solo_nero = const TextStyle( color: Colors.black,);

  const MyApp({super.key});

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

  @override
  Widget build(BuildContext context) {
    
  
    return MaterialApp(
      title: titleApp,
      initialRoute: AccessController.loginWindow,
      onGenerateRoute: AccessController.generateRoute,
      darkTheme: ThemeData.dark(
      
      ),
      theme: ThemeData(
        //scaffoldBackgroundColor: const Color(0xff447A9C),
        //scaffoldBackgroundColor: const Color(0xfff1faee),
        scaffoldBackgroundColor: Colors.white,

        fontFamily: fontApp,
        
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.black,
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
      
      home: LoginWindow(appbar : MyApp.appBarNotBackable),
         
    );
  }
}
