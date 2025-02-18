import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AccessClass/AccessController.dart';

class SingUpWindow extends StatefulWidget {
  const SingUpWindow({
    super.key,
    required this.appbar
  });

  final AppBar appbar;

   

  @override
  State<SingUpWindow> createState() => _SingUpWindowState();

}


class _SingUpWindowState extends State<SingUpWindow> {
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
                  onPressed: (){
                    Navigator.of(context).pushNamed(AccessController.createAgencyWindow);

                  },
                  style: AccessController.clickable_style_button,
                  child: Text('Crea Agenzia')
                ), 
                ElevatedButton(
                  onPressed: (){
                    Navigator.of(context).pushNamed(AccessController.loginWindow);

                  },
                  style: AccessController.clickable_style_button,
                  child: Text('Login')
                ),
                ElevatedButton(
                  onPressed: null,
                  style: AccessController.not_clickable_style_button,
                  child: Text('Sign Up',)
            
                ),
              ],
            ),
            /* 
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

                  icon : Icon(Icons.account_circle_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Email'),
                  border: OutlineInputBorder(),
                  

                  ),
                ),
                
                TextField(
                  obscureText : true,
                  style: TextStyle(
                    color: Colors.black,
                  ), 
                  decoration: InputDecoration(
                    icon : Icon(Icons.key_sharp),
                    iconColor : MyApp.blu ,
                    label: Text('Password'),
            
                    border: OutlineInputBorder(
                      
                    ),
          
            
                  ),
                ),
            
              
              ],
            ),
            ),
            
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