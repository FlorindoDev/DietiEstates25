import 'package:flutter/material.dart';
import 'package:dietiestate25/LoginWindow.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static const String fontApp = 'Goldbill-XLBold';
  static const String titleApp = 'UninaEstate25';
  
  
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: titleApp,
      
      darkTheme: ThemeData.dark(
      
      ),
      theme: ThemeData(
        //scaffoldBackgroundColor: const Color(0xff447A9C),
        scaffoldBackgroundColor: const Color(0xfff1faee),
        
        fontFamily: fontApp,
        
        colorScheme: ColorScheme.fromSeed(
          seedColor: const Color(0x00000000),
          primary: const Color(0xff1D3558),
          secondary:const  Color(0xffE63746),
          tertiary: const Color(0xff447A9C),
          
        
        ),

        textTheme: TextTheme(
          
          bodyMedium: TextStyle(
            fontFamily: fontApp,
            
            fontSize: 40,
          ),
        ), 
        
        useMaterial3: true,
      
      ),
      
      home: LoginWindow(),
    
    );
  }
}
