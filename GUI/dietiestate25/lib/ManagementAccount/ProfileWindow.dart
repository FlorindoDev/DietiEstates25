import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';

class ProfileWindow extends StatefulWidget {
  @override
  State<ProfileWindow> createState() => _ProfileWindowState();
}

class _ProfileWindowState extends State<ProfileWindow> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
          color: MyApp.panna,
          borderRadius: BorderRadius.only(
            topLeft: const Radius.circular(30),
          ),
        ),
        child: Center(
          child: Column(children: [
            ElevatedButton(
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (BuildContext context) {
                    return AlertDialog(
                      title: Text("Conferma"),
                      icon: Icon(Icons.warning_rounded),
                      content: Text("Vuoi proseguire ad uscire"),
                      actions: <Widget>[
                        TextButton(
                          child: Text("Si"),
                          onPressed: () {
                            Navigator.of(context).pop(); // Chiude il dialogo
                            ProfileController.resetJWT();
                            Navigator.of(context)
                                .pushNamed(RouteWindows.loginWindow);
                          },
                        ),
                        TextButton(
                          child: Text("No"),
                          onPressed: () {
                            Navigator.of(context).pop(); // Chiude il dialogo
                          },
                        ),
                      ],
                    );
                  },
                );
              },
              style: ButtonStyle(
                backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                foregroundColor: WidgetStateProperty.all(Colors.white),
              ),
              child: const Text('Esci'),
            )
          ]),
        ),
      ),
    );
  }
}
