import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
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

  late Acquirente copyAgent;

  final Validator validator = Validate();

  bool _isLoading = false;

  @override
  void initState() {
    super.initState();

    copyAgent = Acquirente.fromJson(loggedUser.toJson());

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
      } on Exception catch (e) {
        setState(() {
          _emailError = e.toString();
        });
        setState(() => _isLoading = false);
        return;
      }

      try {
        // nome
        _nameError = null;
        validator.validateName(_nomeController.text);
      } on Exception catch (e) {
        setState(() {
          _nameError = e.toString();
        });
        setState(() => _isLoading = false);
        return;
      }

      try {
        // cognome
        _surnameError = null;
        validator.validateSurname(_cognomeController.text);
      } on Exception catch (e) {
        setState(() {
          _surnameError = e.toString();
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
        await MyApp.mostraPopUpSuccess(context, "Dati aggiornati con successo", null);
        loggedUser = copyAgent;
        Navigator.pop(context, true);
      } else {
        await MyApp.mostraPopUpWarining(context, "Dati non aggiornati", "Non è stto possibile aggiornare i dati del profilo");
        Navigator.pop(context, false);
      }
    }
  }
}
