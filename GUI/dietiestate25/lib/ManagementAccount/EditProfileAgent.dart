import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Validation/Validetor.dart';

class EditProfileAgentPage extends StatefulWidget {
  @override
  _EditProfileAgentPageState createState() => _EditProfileAgentPageState();
}

class _EditProfileAgentPageState extends State<EditProfileAgentPage> {
  final _formKey = GlobalKey<FormState>();
  late TextEditingController _emailController;
  String? _emailError;
  late TextEditingController _nomeController;
  late TextEditingController _cognomeController;
  late TextEditingController _partitaIvaController;

  late AgenteImmobiliare copyAgent;

  final Validator validator = Validate();

  @override
  void initState() {
    super.initState();
    copyAgent = AgenteImmobiliare.fromJson(loggedUser.toJson());
    _emailController = TextEditingController(text: copyAgent.email);
    _nomeController = TextEditingController(text: copyAgent.nome);
    _cognomeController = TextEditingController(text: copyAgent.cognome);
    _partitaIvaController = TextEditingController(text: copyAgent.partitaiva);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Modifica Generalità'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [
              TextFormField(
                controller: _emailController,
                decoration: InputDecoration(
                  labelText: 'Email',
                  errorText: _emailError,
                ),
                keyboardType: TextInputType.emailAddress,
              ),
              TextFormField(
                controller: _nomeController,
                decoration: InputDecoration(labelText: 'Nome'),
                validator: (value) =>
                    value == null || value.isEmpty ? 'Inserisci il nome' : null,
              ),
              TextFormField(
                controller: _cognomeController,
                decoration: InputDecoration(labelText: 'Cognome'),
                validator: (value) => value == null || value.isEmpty
                    ? 'Inserisci il cognome'
                    : null,
              ),
              TextFormField(
                controller: _partitaIvaController,
                decoration: InputDecoration(labelText: 'Partita IVA'),
                validator: (value) => value == null || value.isEmpty
                    ? 'Inserisci la partita IVA'
                    : null,
              ),
              SizedBox(height: 24),
              ElevatedButton(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    // logger.i(_emailController.text);
                    try {
                      validator.validateEmail(_emailController.text);
                    } catch (Exception) {
                            setState(() {
                          _emailError = 'Formato email non valido';
                        });
                      return;
                    }
                    // Aggiorna copyAgent
                    // copyAgent = copyAgent.copyWith(
                    //   email: _emailController.text,
                    //   nome: _nomeController.text,
                    //   cognome: _cognomeController.text,
                    //   partitaiva: _partitaIvaController.text,
                    // );

                    // Ritorna l'agente modificato
                    Navigator.pop(context);
                  }
                },
                child: Text('Salva'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
