import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';

class LoginWindow extends StatefulWidget {
  const LoginWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<LoginWindow> createState() => _LoginWindowState();
}

class _LoginWindowState extends State<LoginWindow> {
  Utente utente = Utente.builder.setId("").setEmail("").build();

  bool _obscureText = true;

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
    return (utente.email != "" && utente.password != "");
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
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: widget.appbar,
      body: Container(
        decoration: const BoxDecoration(
          color: MyApp.panna,
          borderRadius: BorderRadius.only(
            topLeft: const Radius.circular(30),
            topRight: const Radius.circular(30),
            bottomLeft: const Radius.circular(0),
            bottomRight: const Radius.circular(0),
          ),
        ),
        child: Center(
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
                    child: const Text('Crea Agenzia'),
                  ),
                  ElevatedButton(
                    onPressed: null,
                    style: AccessController.not_clickable_style_button,
                    child: const Text('Login'),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                      Navigator.of(context).pushNamed(RouteWindows.singUpWindow);
                    },
                    style: AccessController.clickable_style_button,
                    child: const Text(
                      'Sign Up',
                    ),
                  ),
                ],
              ),
              Container(
                margin: const EdgeInsets.symmetric(horizontal: 40),
                child: Column(
                  spacing: 20,
                  children: [
                    TextField(
                      style: MyApp.stile_testo_solo_nero,
                      decoration: InputDecoration(
                        icon: const Icon(Icons.alternate_email_rounded),
                        iconColor: MyApp.blu,
                        label: const Text('Email'),
                        border: const OutlineInputBorder(),
                      ),
                      onChanged: (e) {
                        utente.email = e;
                        campiCompilatiControl();
                      },
                    ),
                    TextField(
                      obscureText: _obscureText,
                      style: MyApp.stile_testo_solo_nero,
                      decoration: InputDecoration(
                        icon: const Icon(Icons.key_sharp),
                        iconColor: MyApp.blu,
                        label: const Text('Password'),
                        border: const OutlineInputBorder(),
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
                            print("Prima");
                            AccessController.toLogin(utente, context);
                            print("Dopo");
                          } catch (e) {
                            print("Entro");
                            MyApp.mostraPopUpInformativo(context, "Attenzione", e.toString());
                            print("Esco");
                          }
                        }
                      : null,
                  style: ButtonStyle(
                    backgroundColor: coloreBottoneAccedi,
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  ),
                  child: const Text(
                    'Accedi',
                  )),
            ],
          ),
        ),
      ),
    );
  }
}
