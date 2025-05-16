import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:google_sign_in/google_sign_in.dart';
// import 'package:http/http.dart' as http;
// import 'dart:convert';

class SingUpWindow extends StatefulWidget {
  const SingUpWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<SingUpWindow> createState() => _SingUpWindowState();
}

class _SingUpWindowState extends State<SingUpWindow> {
  Utente utente = Utente.builder.setId("").setEmail("").build();
  AccessController accessController = new AccessController();

  bool isCampiCompilati = false;
  var coloreBottoneAccedi = WidgetStateProperty.all<Color>(Colors.grey);

  void setIsCampiCompilati(bool b) {
    setState(() {
      isCampiCompilati = b;
      coloreBottoneAccedi =
          WidgetStateProperty.all<Color>(b ? MyApp.rosso : Colors.grey);
    });
  }

  bool isAllCompilato() {
    return (utente.email != "" &&
        utente.password != "" &&
        utente.nome != "" &&
        utente.cognome != "");
  }

  void campiCompilatiControl() {
    setIsCampiCompilati(isAllCompilato());
  }

  @override
  Widget build(BuildContext context) {
    bool obscureText = true;

    return Scaffold(
      resizeToAvoidBottomInset: true,
      appBar: widget.appbar,
      body: Container(
        height: double.infinity,
        decoration: BoxDecoration(
          color: MyApp.panna,
          borderRadius: BorderRadius.only(
            topLeft: Radius.circular(30),
            topRight: Radius.circular(30),
          ),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                ElevatedButton(
                  onPressed: () {
                    Navigator.pop(context);
                    Navigator.of(context)
                        .pushNamed(RouteWindows.createAgencyWindow);
                  },
                  style: AccessController.clickable_style_button,
                  child: Text('Crea Agenzia'),
                ),
                SizedBox(width: 10),
                ElevatedButton(
                  onPressed: () {
                    Navigator.pop(context);
                    Navigator.of(context).pushNamed(RouteWindows.loginWindow);
                  },
                  style: AccessController.clickable_style_button,
                  child: Text('Login'),
                ),
                SizedBox(width: 10),
                ElevatedButton(
                  onPressed: null,
                  style: AccessController.not_clickable_style_button,
                  child: Text('Sign Up'),
                ),
              ],
            ),
            Expanded(
              child: SingleChildScrollView(
                scrollDirection: Axis.vertical,
                child: Container(
                  margin: EdgeInsets.symmetric(horizontal: 40),
                  child: Column(
                    children: [
                      SizedBox(height: 20),
                      /* Campi Nome, Cognome, Email, Password */
                      TextField(
                        style: MyApp.stile_testo_solo_nero,
                        decoration: InputDecoration(
                          icon: Icon(Icons.account_circle_rounded),
                          iconColor: MyApp.blu,
                          labelText: 'Nome',
                          border: OutlineInputBorder(),
                        ),
                        onChanged: (n) {
                          utente.nome = n;
                          campiCompilatiControl();
                        },
                      ),
                      SizedBox(height: 20),
                      TextField(
                        style: MyApp.stile_testo_solo_nero,
                        decoration: InputDecoration(
                          icon: Icon(Icons.account_circle_rounded),
                          iconColor: MyApp.blu,
                          labelText: 'Cognome',
                          border: OutlineInputBorder(),
                        ),
                        onChanged: (c) {
                          utente.cognome = c;
                          campiCompilatiControl();
                        },
                      ),
                      SizedBox(height: 20),
                      TextField(
                        style: MyApp.stile_testo_solo_nero,
                        decoration: InputDecoration(
                          icon: Icon(Icons.alternate_email_rounded),
                          iconColor: MyApp.blu,
                          labelText: 'Email',
                          border: OutlineInputBorder(),
                        ),
                        onChanged: (e) {
                          utente.email = e;
                          campiCompilatiControl();
                        },
                      ),
                      SizedBox(height: 20),
                      TextField(
                        obscureText: obscureText,
                        style: MyApp.stile_testo_solo_nero,
                        decoration: InputDecoration(
                          icon: Icon(Icons.key_sharp),
                          iconColor: MyApp.blu,
                          labelText: 'Password',
                          border: OutlineInputBorder(),
                          suffixIcon: IconButton(
                            icon: Icon(
                              obscureText
                                  ? Icons.visibility_off
                                  : Icons.visibility,
                            ),
                            onPressed: () =>
                                setState(() => obscureText = !obscureText),
                          ),
                        ),
                        onChanged: (p) {
                          utente.password = p;
                          campiCompilatiControl();
                        },
                      ),
                      SizedBox(height: 20),
                      // Pulsante di registrazione tradizionale
                      ElevatedButton(
                        onPressed: isCampiCompilati
                            ? () {
                                try {
                                  AccessController.toSignUp(utente);
                                } catch (e) {
                                  MyApp.mostraPopUpWarining(
                                      context, "Attenzione", e.toString());
                                }
                              }
                            : null,
                        style: ButtonStyle(
                          backgroundColor: coloreBottoneAccedi,
                          foregroundColor:
                              WidgetStateProperty.all(Colors.white),
                        ),
                        child: Text('Registrati'),
                      ),
                      SizedBox(height: 20),
                      // Pulsante Google Sign-Up
                      ElevatedButton.icon(
                        icon: Image.asset(
                          'assets/google_logo.png',
                          height: 18,
                        ),
                        label: Text('Registrati con Google'),
                        onPressed: () {
                          accessController.handleGoogleSignUp(context);
                        },
                        style: ButtonStyle(
                          backgroundColor:
                              WidgetStateProperty.all(Colors.white),
                          foregroundColor:
                              WidgetStateProperty.all(Colors.black),
                          overlayColor:
                              WidgetStateProperty.resolveWith<Color?>((states) {
                            if (states.contains(MaterialState.pressed))
                              return MyApp.panna;
                            return null;
                          }),
                          shape:
                              WidgetStateProperty.all<RoundedRectangleBorder>(
                            RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(8.0),
                            ),
                          ),
                        ),
                      ),
                      SizedBox(height: 20),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
