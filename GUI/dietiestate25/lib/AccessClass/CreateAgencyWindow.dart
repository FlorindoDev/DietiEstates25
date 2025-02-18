import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';

class CreateAgencyWindow extends StatefulWidget {
  const CreateAgencyWindow({
    super.key,
    required this.appbar
  });

  final AppBar appbar;

   

  @override
  State<CreateAgencyWindow> createState() => _CreateAgencyWindowState();


}

class _CreateAgencyWindowState extends State<CreateAgencyWindow> {
  bool isCampiCompilati = false;
  


  @override
  Widget build(BuildContext context) {
    

    return Scaffold(
      
      appBar: widget.appbar,
      
      body: Container(
         decoration: BoxDecoration(
          color: MyApp.panna,
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(30),
              topRight: Radius.circular(30),
              bottomLeft: Radius.circular(0),
              bottomRight: Radius.circular(0),
            ),
          ),

        child:Center(
        
        child : Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              spacing: 10,
              children: <Widget>[
                ElevatedButton(
                  onPressed: null,
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(MyApp.celeste),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  ),
                  child: Text('Crea Agenzia')
                ), 
                ElevatedButton(
                  onPressed: (){
                    Navigator.of(context).pushNamed(AccessController.loginWindow);

                  },
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(MyApp.blu),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  ),
                  child: Text('Login')
                ),
                ElevatedButton(
                  onPressed: (){
                    Navigator.of(context).pushNamed(AccessController.singUpWindow);

                  },
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(MyApp.blu),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  ),
                  child: Text('Sign Up',)
            
                ),
              ],
            ),
            
            Container(
            margin: EdgeInsets.symmetric(horizontal: 40),
            
            child:Column(
              spacing: 20,
              
              children: [
                
                TextField(
                  style: TextStyle(
                    color: Colors.black,
                  ),      
                  decoration: InputDecoration(

                  icon : Icon(Icons.add_business_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Nome Agenzia'),
                  border: OutlineInputBorder(),
                  

                  ),
                ),
                
                TextField(
                  
                  style: TextStyle(
                    color: Colors.black,
                  ), 
                  decoration: InputDecoration(
                    icon : Icon(Icons.app_registration_rounded ),
                    iconColor : MyApp.blu ,
                    label: Text('Partita IVA'),
            
                    border: OutlineInputBorder(
                      
                    ),
          
            
                  ),
                ),

                TextField(
                  style: TextStyle(
                    color: Colors.black,
                  ),      
                  decoration: InputDecoration(

                  icon : Icon(Icons.map_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Sede'),
                  border: OutlineInputBorder(),
                  

                  ),
                ),

                TextField(
                  style: TextStyle(
                    color: Colors.black,
                  ),      
                  decoration: InputDecoration(

                  icon : Icon(Icons.account_circle_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Email'),
                  border: OutlineInputBorder(),
                  

                  ),
                ),
              
              ],
            ),
            ),
            /* 
            ElevatedButton(
                  onPressed: login,
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                    
                  ),
                  child: Text('Accedi',)
                ),  */
          ],
        ),
        ),
      )
    );
  }
}