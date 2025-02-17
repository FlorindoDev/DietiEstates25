import 'package:flutter/material.dart';


class LoginWindow extends StatefulWidget {
  const LoginWindow({
    super.key
  });

  @override
  State<LoginWindow> createState() => _LoginWindowState();
}

class _LoginWindowState extends State<LoginWindow> {
  
  void goToCreateAgency() {
    setState(() {
      
    });
  }
  void goToLogin() {
    setState(() {
      
    });
  }
  void goToSignUp() {
    setState(() {
      
    });
  }
  void login() {
    setState(() {
      
    });
  }
  
  
  @override
  Widget build(BuildContext context) {
    

    return Scaffold(
      
      appBar: AppBar(
        centerTitle: true,
  
        
        title: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              
              Image.asset(
                'images/logo/logo_piccolo.jpg',
                height: 50, 
                width: 50,
                
              ),
              Row(
                children: [
                    Text('UninaEstates'),
                    Text('25',
                      style: TextStyle(
                      color: Color(0xffE63746),
                      ),
                    )
                ],
              ),
              

            ],
          ),
            
      ),
      
      body: Center(
        
        child : Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              spacing: 10,
              children: <Widget>[
                ElevatedButton(
                  onPressed: goToCreateAgency,
                  child: Text('Crea Agenzia'),
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(Color(0xff447A9C)),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  )
                ), 
                ElevatedButton(
                  onPressed: goToLogin,
                  child: Text('Login'),
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(Color(0xff1D3558)),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  )
                ),
                ElevatedButton(
                  onPressed: goToSignUp,
                  child: Text('Sign Up',),
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(Color(0xff447A9C)),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  )
            
                ),
              ],
            ),
             
            Container(
            margin: EdgeInsets.symmetric(horizontal: 10),
            child:Column(
              spacing: 20,
              
              children: [
                
                TextField(
                        
                  decoration: InputDecoration(

                  icon : Icon(Icons.account_circle_rounded),
                  iconColor : Color(0xff1D3558) ,
                  label: Text('Nome Utente'),
                  border: OutlineInputBorder(),

                  ),
                ),
                
                TextField(
                  obscureText : true,
                   
                  decoration: InputDecoration(
                    icon : Icon(Icons.key_sharp),
                    iconColor : Color(0xff1D3558) ,
                    label: Text('Password',
                      style: TextStyle(
                        color: Colors.black,
                      ),
                    ),
            
                    border: OutlineInputBorder(
                      
                    ),
          
            
                  ),
                ),
            
              
              ],
            ),
            ),
            
            ElevatedButton(
                  onPressed: login,
                  child: Text('Accedi',),
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(Color(0xffE63746)),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  )
                ), 
          ],
        ),
      )
    );
  }
}