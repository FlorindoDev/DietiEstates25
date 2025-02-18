import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';

class LoginWindow extends StatefulWidget {
  const LoginWindow({
    super.key,
    required this.appbar
  });

  final AppBar appbar;

   

  @override
  State<LoginWindow> createState() => _LoginWindowState();


}

class _LoginWindowState extends State<LoginWindow> {
  String email = "";
  String password = "";
  bool isCampiCompilati = false;
  
  void setIsCampiCompilati(bool b) {
    setState(() {
      isCampiCompilati = b;
    });
  }

  void campiCompilatiControl(){
      print(email);
      print(password);
      print(email != "");
      print(password != "");
      if(email != "" && password != ""){
        setIsCampiCompilati(true);
      }else{
        setIsCampiCompilati(false);
      }
  }

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
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(MyApp.blu),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  ),
                  child: Text('Crea Agenzia')
                ), 
                ElevatedButton(
                  onPressed: null,
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(MyApp.celeste),
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

                  icon : Icon(Icons.account_circle_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Email'),
                  border: OutlineInputBorder(),
                  
                  

                  ),
                  onChanged: (e){
                    email = e;
                    campiCompilatiControl();

                  },
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
                  onChanged: (p){
                    password = p;
                    
                    campiCompilatiControl();

                  },
                ),
            
              
              ],
            ),
            ),
            
            ElevatedButton(
                  onPressed: isCampiCompilati ? (){print("Premuto");} : null,
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                    
                  ),
                  child: Text('Accedi',)
                ), 
          ],
        ),
      ),),
    );
  }
}