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
  var coloreBottoneAccedi = WidgetStateProperty.all<Color>(Colors.grey);
  String _email = "";
  String _nome_agenzia = "";
  String _sede = "";
  String _partitaIVA = "";
  
  String get email => _email;

  set email(String value) {
    _email = value;
  }

  String get nome_agenzia => _nome_agenzia;

  set nome_agenzia(String value) {
    _nome_agenzia = value;
  }

  String get sede => _sede;

  set sede(String value) {
    _sede = value;
  }

  String get partitaIVA => _partitaIVA;

  set partitaIVA(String value) {
    _partitaIVA = value;
  }

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
    return (email != "" && nome_agenzia != "" && sede != "" && partitaIVA != "");
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
                  onPressed: null,
                  style: AccessController.not_clickable_style_button,
                  child: Text('Crea Agenzia')
                ), 
                ElevatedButton(
                  onPressed: (){
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed(AccessController.loginWindow);

                  },
                  style: AccessController.clickable_style_button,
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
                  style: MyApp.stile_testo_solo_nero ,      
                  decoration: InputDecoration(

                  icon : Icon(Icons.add_business_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Nome Agenzia'),
                  border: OutlineInputBorder(),
                  
                  

                  ),
                  onChanged: (n){
                    nome_agenzia = n;
                    campiCompilatiControl();

                  }
                ),
                
                TextField(
                  
                  style: MyApp.stile_testo_solo_nero, 
                  decoration: InputDecoration(
                    icon : Icon(Icons.app_registration_rounded ),
                    iconColor : MyApp.blu ,
                    label: Text('Partita IVA'),
            
                    border: OutlineInputBorder(
                      
                    ),
          
            
                  ),
                  onChanged: (iva){
                    partitaIVA = iva;
                    campiCompilatiControl();
                  }
                ),

                TextField(
                  style: MyApp.stile_testo_solo_nero,      
                  decoration: InputDecoration(

                  icon : Icon(Icons.map_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Sede'),
                  border: OutlineInputBorder(),
                  

                  ),
                  onChanged: (s){
                    sede = s;
                    campiCompilatiControl();
                  }
                ),

                TextField(
                  style: MyApp.stile_testo_solo_nero,      
                  decoration: InputDecoration(

                  icon : Icon(Icons.account_circle_rounded),
                  iconColor : MyApp.blu ,
                  label: Text('Email'),
                  border: OutlineInputBorder(),
                  

                  ),
                  onChanged: (e){
                    email = e;
                    campiCompilatiControl();
                  }
                ),
              
              ],
            ),
            ),
            
            ElevatedButton(
                  onPressed: isCampiCompilati ? (){
                      try{
                        AccessController.toCreateAgency(email,nome_agenzia,sede,partitaIVA);
                      }catch(e){
                        AccessController.mostraPopUp(context,"Attenzione",e.toString());
                      }
                      
                    }: null,
                  style: ButtonStyle(
                    backgroundColor: coloreBottoneAccedi,
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                    
                  ),
                  child: Text('Crea Agenzia',)
                ), 
          ],
        ),
        ),
      )
    );
  }
}