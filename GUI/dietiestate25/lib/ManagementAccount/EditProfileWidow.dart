import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Validation/Validetor.dart';

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class EditProfilePage extends StatefulWidget {
  @override
  _EditProfilePageState createState() => _EditProfilePageState();
}

class _EditProfilePageState extends State<EditProfilePage> {
  final _formKey = GlobalKey<FormState>();

  late TextEditingController _emailController;
  String? _emailError;
  late TextEditingController _nomeController;
  String? _nameError;
  late TextEditingController _cognomeController;
  String? _surnameError;

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

    _emailController = TextEditingController(text: copyAgent.email);
    _nomeController = TextEditingController(text: copyAgent.nome);
    _cognomeController = TextEditingController(text: copyAgent.cognome);
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
                controller: _emailController,
                decoration: InputDecoration(
                  labelText: 'Email',
                  errorText: _emailError,
                ),
                keyboardType: TextInputType.emailAddress,
              ),
              TextFormField(
                controller: _nomeController,
                decoration: InputDecoration(
                  labelText: 'Nome',
                  errorText: _nameError,
                ),
                validator: (value) =>
                    value == null || value.isEmpty ? 'Inserisci il nome' : null,
              ),
              TextFormField(
                controller: _cognomeController,
                decoration: InputDecoration(
                  labelText: 'Cognome',
                  errorText: _surnameError,
                ),
                validator: (value) => value == null || value.isEmpty
                    ? 'Inserisci il cognome'
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
        // email
        _emailError = null;
        validator.validateEmail(_emailController.text);
      } on Exception {
        setState(() {
          _emailError = "Email non valida";
        });
        setState(() => _isLoading = false);
        return;
      }

      try {
        // nome
        _nameError = null;
        validator.validateName(_nomeController.text);
      } on Exception {
        setState(() {
          _nameError = "Nome non valido";
        });
        setState(() => _isLoading = false);
        return;
      }

      try {
        // cognome
        _surnameError = null;
        validator.validateSurname(_cognomeController.text);
      } on Exception {
        setState(() {
          _surnameError = "Cognome non valido";
        });
        setState(() => _isLoading = false);
        return;
      }

      copyAgent.email = _emailController.text;
      copyAgent.nome = _nomeController.text;
      copyAgent.cognome = _cognomeController.text;

      // await Future.delayed(Duration(seconds: 5));

      bool exitState = await ProfileController.updateProfile(copyAgent);

      logger.e(exitState);

      setState(() => _isLoading = false);
      if (exitState) {
        if (loggedUser.email != copyAgent.email) {
          loggedUser = copyAgent;
          await ProfileController.resetJWT();
          await MyApp.mostraPopUpSuccess(context, "Email modificata, si prega di riefettuare  il login", null);
          Navigator.pushNamed(context, RouteWindows.loginWindow);
        }else{
            loggedUser = copyAgent;
            await MyApp.mostraPopUpSuccess(context, "Dati aggiornati con successo", null);
            Navigator.pop(context, true);
        }
      } else {
        await MyApp.mostraPopUpWarining(context, "Dati non aggiornati",
            "Non è stto possibile aggiornare i dati del profilo");
        Navigator.pop(context, false);
      }
    }
  }
}
