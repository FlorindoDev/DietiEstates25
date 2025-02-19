import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';
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
  
  Utente utente = Utente.builder
    .setId(0)
    .setEmail("")
    .build();

  bool _obscureText = true;

  bool isCampiCompilati = false;
  var coloreBottoneAccedi = WidgetStateProperty.all<Color>(Colors.grey);

  void setIsCampiCompilati(bool b) {
    setState(() {
      isCampiCompilati = b;
      if(b){
        coloreBottoneAccedi = WidgetStateProperty.all<Color>(MyApp.rosso);
      }else{
        coloreBottoneAccedi = WidgetStateProperty.all<Color>(Colors.grey);
      }
    });
  }

  

  bool isAllCompilato(){
    return (utente.email != "" && utente.password != "");
  }

  void campiCompilatiControl(){
      if(isAllCompilato()){
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
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed(AccessController.createAgencyWindow);

                  },
                  style: AccessController.clickable_style_button,
                  child: Text('Crea Agenzia')
                ), 
                ElevatedButton(
                  onPressed: null,
                  style: AccessController.not_clickable_style_button,
                  child: Text('Login')
                ),
                ElevatedButton(
                  onPressed: (){
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed(AccessController.singUpWindow);
                  },
                  style: AccessController.clickable_style_button,
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
                  style: MyApp.stile_testo_solo_nero,      
                  decoration: InputDecoration(

                  icon : Icon(Icons.alternate_email_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Email'),
                  border: OutlineInputBorder(),
                  
                  

                  ),
                  onChanged: (e){
                    utente.email = e;
                    campiCompilatiControl();

                  },
                ),
                
                TextField(
                  obscureText : _obscureText,
                  style: MyApp.stile_testo_solo_nero, 
                  decoration: InputDecoration(
                    icon : Icon(Icons.key_sharp),
                    iconColor : MyApp.blu ,
                    label: Text('Password'),
            
                    border: OutlineInputBorder(
                      
                    ),
                    
                    suffixIcon: IconButton(
                      icon: Icon(
                        _obscureText ? Icons.visibility_off : Icons.visibility,
                      ),
                    
                      onPressed: () {
                        setState(() {
                          _obscureText = !_obscureText;
                        });
                      },
                    ),
                  ),
                  
                  onChanged: (p){
                    utente.password = p;
                    
                    campiCompilatiControl();

                  },
                ),
            
              
              ],
            ),
            ),
            
            ElevatedButton(
                  onPressed: 
                    isCampiCompilati ? (){
                      try{
                        AccessController.toLogin(utente);
                      }catch(e){
                        AccessController.mostraPopUp(context,"Attenzione",e.toString());
                      }
                      
                    }: null,
                  style: ButtonStyle(
                    backgroundColor: coloreBottoneAccedi,
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