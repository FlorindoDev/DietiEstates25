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

  const MyApp({super.key});

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
            
            fontSize: 40,
          ),
        ), 
        
        useMaterial3: true,
      
      ),
      
      home: LoginWindow(appbar : AccessController.appBarNotBackable),
         
    );
  }
}
