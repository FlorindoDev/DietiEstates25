import 'dart:convert';
import 'dart:typed_data';

import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Validation/Validetor.dart';

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

class EditPasswordPage extends StatefulWidget {
  @override
  _EditPasswordPageState createState() => _EditPasswordPageState();
}

class _EditPasswordPageState extends State<EditPasswordPage> {
  final _formKey = GlobalKey<FormState>();

  late TextEditingController _oldPassController;
  String? _oldPassError;
  late TextEditingController _new1PassController;
  String? _new1PassError;
  late TextEditingController _new2PassController;
  String? _new2PassError;

  final Validator validator = Validate();

  bool _isLoading = false;

  @override
  void initState() {
    super.initState();

    _oldPassController = TextEditingController();
    _new1PassController = TextEditingController();
    _new2PassController = TextEditingController();

  }

  @override
  Widget build(BuildContext context) {
    
    return Scaffold(
      appBar: AppBar(
        title: Text('Modifica Password'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [

              TextFormField(
                controller: _oldPassController,
                decoration: InputDecoration(
                  labelText: 'Vecchia Password',
                  errorText: _oldPassError,
                ),
                keyboardType: TextInputType.emailAddress,
              ),
              TextFormField(
                controller: _new1PassController,
                decoration: InputDecoration(
                  labelText: 'Nuova Password',
                  errorText: _new1PassError,
                ),
                validator: (value) =>
                    value == null || value.isEmpty ? 'Inserisci il nome' : null,
              ),
              TextFormField(
                controller: _new2PassController,
                decoration: InputDecoration(
                  labelText: 'Conferma Nuova Password',
                  errorText: _new2PassError,
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
        _oldPassError = null;
        validator.validateEmail(_oldPassController.text);
      } on Exception catch (e) {
        setState(() {
          _oldPassError = e.toString();
        });
        setState(() => _isLoading = false);
        return;
      }

      if (_new1PassController.text != _new2PassController.text){
        setState(() {
          _new1PassError = "Le password non coincidono";
        });
      }

      // await Future.delayed(Duration(seconds: 5));

      // bool exitState = await ProfileController.updateProfile();

      // logger.e(exitState);

      // setState(() => _isLoading = false);
      // if (exitState) {
      //   await MyApp.mostraPopUpSuccess(context, "Password aggiornata con successo", null);
      //   // loggedUser.password = ;
      //   Navigator.pop(context, true);
      // } else {
      //   await MyApp.mostraPopUpWarining(context, "Password non aggiornata", "Non è stto possibile aggiornare la password");
      //   Navigator.pop(context, false);
      // }
    }
  }
}
