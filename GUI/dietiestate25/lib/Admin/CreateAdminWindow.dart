import 'package:dietiestate25/Admin/AdminHomeController.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';


class CreateAdminWindow extends StatefulWidget {
  const CreateAdminWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<CreateAdminWindow> createState() => _CreateAdminWindowState();
}

class _CreateAdminWindowState extends State<CreateAdminWindow>{
  bool _obscureText = true;
  bool isCampiCompilati = false;


  Amministratore utente = Amministratore.builder
      .setId("")
      .setEmail("")
      .setName("")
      .setCognome("")
      .setPassword("")
      .setPartitaiva(loggedUser.partitaiva ?? "")
      .setIssupportoammi(false)
      .build();

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

    void campiCompilatiControl() {
    if (isAllCompilato()) {
      setIsCampiCompilati(true);
    } else {
      setIsCampiCompilati(false);
    }
  }

  bool isAllCompilato() {
    return (utente.email != "" && utente.password != "" && utente.nome != "" && utente.cognome != "");
  }

  @override
  Widget build(BuildContext context) {
    
    return Scaffold(
      appBar: widget.appbar,
      body: Container(
        width: double.infinity,
        height: double.infinity,
        decoration: BoxDecoration(
          color: MyApp.celeste,
          borderRadius: BorderRadius.only(topLeft: Radius.circular(30), topRight: Radius.circular(30)),
        ),
        padding: const EdgeInsets.only(top: 20,left: 20, right: 20),
        child:
        SingleChildScrollView(
        
        child:  Container(
          padding: const EdgeInsets.all(20),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(20),
          ),
          child: Column(
          spacing: 20,
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Text(
              "Crea Amministratore",
              textAlign: TextAlign.center,
              style: TextStyle(
                fontFamily: MyApp.fontApp,
                fontSize: 25,
                color: MyApp.rosso,
              ),
            ),
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
              
              style: MyApp.stile_testo_solo_nero,
              decoration: InputDecoration(
                icon: const Icon(Icons.text_fields_rounded),
                iconColor: MyApp.blu,
                label: const Text('Nome'),
                border: const OutlineInputBorder(),
              ),
              onChanged: (e) {
                  utente.nome = e;
                  campiCompilatiControl();
                },
              ),

            TextField(
              
              style: MyApp.stile_testo_solo_nero,
              decoration: InputDecoration(
                icon: const Icon(Icons.text_fields_rounded),
                iconColor: MyApp.blu,
                label: const Text('Cognome'),
                border: const OutlineInputBorder(),
              ),
              onChanged: (e) {
                  utente.cognome = e;
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
                                  _obscureText
                                      ? Icons.visibility_off
                                      : Icons.visibility,
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
            Row(
            mainAxisAlignment: MainAxisAlignment.center,
              children: [
              Checkbox(
              semanticLabel: "Supporto Amministratore",
              value: utente.issupportoammi, 
              onChanged: (value) {
              setState(() {
                utente.issupportoammi = value!;
              });
            }),
              Text("Supporto Amministratore")


            ],),
            
const SizedBox(height: 10),
              ElevatedButton(
                  onPressed: isCampiCompilati
                      ? () {
                          AdminHomeController.createAdmin(context, utente, "");
                          
                        }
                      : null,
                  style: ButtonStyle(
                    backgroundColor: coloreBottoneAccedi,
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                  ),
                  child: const Text('Accedi'),
                ),
            
          ],
        ),
        ),
        ),
      ),  
      );
     
  }
  
    
}