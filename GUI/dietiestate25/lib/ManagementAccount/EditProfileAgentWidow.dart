import 'dart:convert';
import 'dart:typed_data';

import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/Validation/Validate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Validation/Validetor.dart';

import 'package:dietiestate25/Logger/logger.dart';

final logger = MyLogger.getIstance();

Uint8List base64ToBytes(String b64) => base64Decode(b64);

class EditProfileAgentPage extends StatefulWidget {
  @override
  _EditProfileAgentPageState createState() => _EditProfileAgentPageState();
}

class _EditProfileAgentPageState extends State<EditProfileAgentPage> {
  final _formKey = GlobalKey<FormState>();

  final ImagePicker _picker = ImagePicker();

  late TextEditingController _emailController;
  String? _emailError;
  late TextEditingController _nomeController;
  String? _nameError;
  late TextEditingController _cognomeController;
  String? _surnameError;
  late TextEditingController _partitaIvaController;
  String? _partitaIVAError;
  late TextEditingController _biografiaController;
  String? _biografiaError;

  late AgenteImmobiliare copyAgent;

  final Validator validator = Validate();

  bool _isLoading = false;

  @override
  void initState() {
    super.initState();

    copyAgent = AgenteImmobiliare.fromJson(loggedUser.toJson());

    _emailController = TextEditingController(text: copyAgent.email);
    _nomeController = TextEditingController(text: copyAgent.nome);
    _cognomeController = TextEditingController(text: copyAgent.cognome);
    _partitaIvaController = TextEditingController(text: copyAgent.partitaiva);
    _biografiaController = TextEditingController(text: copyAgent.biografia);
  }

  Future<void> _pickImage() async {
    final XFile? file = await _picker.pickImage(source: ImageSource.gallery);
    if (file == null) return;
    final bytes = await file.readAsBytes();
    final newBase64 = base64Encode(bytes);

    setState(() {
      // Aggiorno direttamente la stringa base64
      copyAgent.immagineprofile = newBase64;
    });
  }

  @override
  Widget build(BuildContext context) {
    String img = copyAgent.immagineprofile ?? '';
    Uint8List imgBytes = base64Decode(img);

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
              GestureDetector(
                onTap: _pickImage,
                child: Container(
                  width: 96,
                  height: 96,
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    image: DecorationImage(
                      image: MemoryImage(imgBytes),
                      fit: BoxFit.scaleDown,
                      alignment: Alignment.center,
                    ),
                    // border: Border.all(color: Colors.grey.shade400, width: 2),
                  ),
                ),
              ),
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
              TextFormField(
                controller: _partitaIvaController,
                decoration: InputDecoration(
                  labelText: 'Partita IVA',
                  errorText: _partitaIVAError,
                ),
                validator: (value) => value == null || value.isEmpty
                    ? 'Inserisci la partita IVA'
                    : null,
              ),
              TextFormField(
                controller: _biografiaController,
                decoration: InputDecoration(
                  labelText: 'Biografia',
                  errorText: _biografiaError,
                ),
                validator: (value) => value == null || value.isEmpty
                    ? 'Inserisci Biografia'
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

      try {
        // partita iva
        _partitaIVAError = null;
        validator.validatePartitalVA(_partitaIvaController.text);
      } on Exception catch (e) {
        setState(() {
          _partitaIVAError = e.toString();
        });
        setState(() => _isLoading = false);
        return;
      }

      copyAgent.email = _emailController.text;
      copyAgent.nome = _nomeController.text;
      copyAgent.cognome = _cognomeController.text;
      copyAgent.partitaiva = _partitaIvaController.text;
      copyAgent.biografia = _biografiaController.text;

      // await Future.delayed(Duration(seconds: 5));
      bool exitState = await ProfileController.updateProfile(copyAgent);

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
