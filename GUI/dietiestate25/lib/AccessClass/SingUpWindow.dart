import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AccessClass/AccessController.dart';

class SingUpWindow extends StatefulWidget {
  const SingUpWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<SingUpWindow> createState() => _SingUpWindowState();
}

class _SingUpWindowState extends State<SingUpWindow> {
  Utente utente = Utente.builder.setId("").setEmail("").build();

  bool isCampiCompilati = false;
  var coloreBottoneAccedi = WidgetStateProperty.all<Color>(Colors.grey);

  void setIsCampiCompilati(bool b) {
    setState(() {
      isCampiCompilati = b;
      if (b) {
        coloreBottoneAccedi = WidgetStateProperty.all<Color>(MyApp.rosso);
      } else {
        coloreBottoneAccedi = WidgetStateProperty.all<Color>(Colors.grey);
      }
    });
  }

  bool isAllCompilato() {
    return (utente.email != "" && utente.password != "" && utente.nome != "" && utente.cognome != "");
  }

  void campiCompilatiControl() {
    if (isAllCompilato()) {
      setIsCampiCompilati(true);
    } else {
      setIsCampiCompilati(false);
    }
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
              bottomLeft: Radius.circular(0),
              bottomRight: Radius.circular(0),
            ),
          ),
          child: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            child: Center(
              child: Expanded(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceAround,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      spacing: 10,
                      children: <Widget>[
                        ElevatedButton(
                            onPressed: () {
                              Navigator.pop(context);
                              Navigator.of(context).pushNamed(RouteWindows.createAgencyWindow);
                            },
                            style: AccessController.clickable_style_button,
                            child: Text('Crea Agenzia')),
                        ElevatedButton(
                            onPressed: () {
                              Navigator.pop(context);
                              Navigator.of(context).pushNamed(RouteWindows.loginWindow);
                            },
                            style: AccessController.clickable_style_button,
                            child: Text('Login')),
                        ElevatedButton(
                            onPressed: null,
                            style: AccessController.not_clickable_style_button,
                            child: Text(
                              'Sign Up',
                            )),
                      ],
                    ),
                    Container(
                      margin: EdgeInsets.symmetric(horizontal: 40),
                      child: Column(
                        spacing: 20,
                        children: [
                          TextField(
                            style: MyApp.stile_testo_solo_nero,
                            decoration: InputDecoration(
                              icon: Icon(Icons.account_circle_rounded),
                              iconColor: MyApp.blu,
                              label: Text('Nome'),
                              border: OutlineInputBorder(),
                            ),
                            onChanged: (n) {
                              utente.nome = n;
                              campiCompilatiControl();
                            },
                          ),
                          TextField(
                            style: MyApp.stile_testo_solo_nero,
                            decoration: InputDecoration(
                              icon: Icon(Icons.account_circle_rounded),
                              iconColor: MyApp.blu,
                              label: Text('Cognome'),
                              border: OutlineInputBorder(),
                            ),
                            onChanged: (c) {
                              utente.cognome = c;
                              campiCompilatiControl();
                            },
                          ),
                          TextField(
                            style: MyApp.stile_testo_solo_nero,
                            decoration: InputDecoration(
                              icon: Icon(Icons.alternate_email_rounded),
                              iconColor: MyApp.blu,
                              label: Text('Email'),
                              border: OutlineInputBorder(),
                            ),
                            onChanged: (e) {
                              utente.email = e;
                              campiCompilatiControl();
                            },
                          ),
                          TextField(
                            obscureText: obscureText,
                            style: MyApp.stile_testo_solo_nero,
                            decoration: InputDecoration(
                              icon: Icon(Icons.key_sharp),
                              iconColor: MyApp.blu,
                              label: Text('Password'),
                              border: OutlineInputBorder(),
                              suffixIcon: IconButton(
                                icon: Icon(
                                  obscureText ? Icons.visibility_off : Icons.visibility,
                                ),
                                onPressed: () {
                                  setState(() {
                                    obscureText = !obscureText;
                                  });
                                },
                              ),
                            ),
                            onChanged: (p) {
                              utente.password = p;

                              campiCompilatiControl();
                            },
                          ),
                        ],
                      ),
                    ),
                    ElevatedButton(
                        onPressed: isCampiCompilati
                            ? () {
                                try {
                                  AccessController.toSignUp(utente);
                                } catch (e) {
                                  MyApp.mostraPopUpInformativo(context, "Attenzione", e.toString());
                                }
                              }
                            : null,
                        style: ButtonStyle(
                          backgroundColor: coloreBottoneAccedi,
                          foregroundColor: WidgetStateProperty.all(Colors.white),
                        ),
                        child: Text(
                          'Registrati',
                        )),
                  ],
                ),
              ),
            ),
          ),
        ));
  }
}
