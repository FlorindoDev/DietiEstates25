import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Validation/Validetor.dart';

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class EditPassword extends StatefulWidget {
  @override
  _EditPasswordState createState() => _EditPasswordState();
}

class _EditPasswordState extends State<EditPassword> {
  final _formKey = GlobalKey<FormState>();

  late TextEditingController _passwordController;
  String? _passwordError;

  late Utente copyAgent;

  final Validator validator = Validate();

  bool _isLoading = false;

  @override
  void initState() {
    super.initState();

    if (loggedUser is Amministratore)
      copyAgent = Amministratore.fromJson(loggedUser.toJson());

    if (loggedUser is Acquirente)
      copyAgent = Acquirente.fromJson(loggedUser.toJson());
    // copyAgent = Acquirente.fromJson(loggedUser.toJson());

    _passwordController = TextEditingController(text: "");
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
              const SizedBox(height: 24),
              TextFormField(
                controller: _passwordController,
                decoration: InputDecoration(
                  labelText: 'Password',
                  errorText: _passwordError,
                ),
                validator: (value) => value == null || value.isEmpty
                    ? 'Inserisci il Password'
                    : null,
              ),
              SizedBox(height: 24),
              _isLoading
                  ? Center(child: CircularProgressIndicator())
                  : ElevatedButton(
                      onPressed: _onSavePressed,
                      child: Text('Salva'),
                    ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> _onSavePressed() async {
    if (_formKey.currentState!.validate()) {
      setState(() => _isLoading = true);

      try {
        // password
        _passwordError = null;
        validator.validatePassword(_passwordController.text);
      } on Exception {
        setState(() {
          _passwordError = "password non valido";
        });
        setState(() => _isLoading = false);
        return;
      }

      copyAgent.password = _passwordController.text;

      // await Future.delayed(Duration(seconds: 5));

      bool exitState = await ProfileController.updateProfilePassword(copyAgent);

      logger.e(exitState);

      setState(() => _isLoading = false);
      if (exitState) {
        await MyApp.mostraPopUpSuccess(
            context, "Dati aggiornati con successo", null);
        loggedUser = copyAgent;
        Navigator.pop(context, true);
      } else {
        await MyApp.mostraPopUpWarining(context, "Dati non aggiornati",
            "Non è stto possibile aggiornare i dati del profilo");
        Navigator.pop(context, false);
      }
    }
  }
}
