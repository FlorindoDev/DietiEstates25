import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import './EditProfileAgent.dart';

import 'package:dietiestate25/Model/Utente/Utente.dart';

import 'dart:convert';
import 'dart:typed_data';

class ProfileAgentWindow extends StatefulWidget {
  @override
  State<ProfileAgentWindow> createState() => _ProfileAgentWindowState();
}

// Uint8List hexStringToUint8List(String hexString) {
//   hexString = hexString.replaceAll(' ', '');
//   if (hexString.length % 2 != 0) {
//     throw FormatException('Hex string must have an even number of characters');
//   }
//   List<int> bytes = [];
//   for (int i = 0; i < hexString.length; i += 2) {
//     String hexPair = hexString.substring(i, i + 2);
//     bytes.add(int.parse(hexPair, radix: 16));
//   }
//   return Uint8List.fromList(bytes);
// }

Uint8List base64ToBytes(String b64) => base64Decode(b64);

class _ProfileAgentWindowState extends State<ProfileAgentWindow> {
  bool _notificationsEnabled = true; // switch principale
  // bool? _emailNotif;
  // bool? _pushNotif;
  // bool? _smsNotif;

  final String base64String = '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';

  LoadState _loadState = LoadState.loading;
  @override
  void initState() {
    super.initState();
    _loadProfile();
  }

  Future<void> _loadProfile() async {
    bool exitState = await ProfileController.getProfile(loggedUser.email);
    setState(() {
      _loadState = exitState ? LoadState.success : LoadState.error;
    });
  }

  Future<void> _refresh() async {
    bool exitState = await ProfileController.refreshProfile();
    setState(() {
      _loadState = exitState ? LoadState.success : LoadState.error;
    });
  }

  @override
  Widget build(BuildContext context) {
    switch (_loadState) {
      case LoadState.loading:
        return Scaffold(
          body: Center(child: CircularProgressIndicator()),
        );

      case LoadState.error:
        return Scaffold(
          body: Center(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Icon(Icons.error, size: 64, color: Colors.red),
                const SizedBox(height: 16),
                Text("Impossibile caricare il profilo"),
                const SizedBox(height: 16),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ElevatedButton(
                      onPressed: () {
                        setState(() {
                          _loadState = LoadState.loading;
                        });
                        _refresh();
                      },
                      child: Text("Riprova"),
                    ),
                    ElevatedButton(
                      onPressed: () {
                        _showSignOutDialog(context);
                      },
                      child: Text("Esci"),
                    ),
                  ],
                ),
              ],
            ),
          ),
        );

      case LoadState.success:
      Uint8List bytes = base64Decode(base64String);
        return Scaffold(
          backgroundColor: MyApp.panna,
          body: Container(
            width: double.infinity,
            decoration: const BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.only(
                topLeft: Radius.circular(30),
              ),
            ),
            child: Column(
              children: [
                const SizedBox(height: 24),
                ClipOval(
                child: Image.memory(bytes,
                    fit: BoxFit.cover,
                    width: 96,
                    height: 96,
                  ),
                ),
                const SizedBox(height: 16),
                Expanded(
                  child: ListView(
                    children: [
                      Divider(),
                      Padding(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 16.0, vertical: 8.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              children: [
                                Icon(Icons.account_box, color: Colors.red),
                                SizedBox(width: 8),
                                Text(
                                  "Informazioni Account",
                                  style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 16),
                                ),
                              ],
                            ),
                            SizedBox(height: 8),
                            Text("Tipo Account: ${loggedUserType.name}"),
                            Text("Email: ${loggedUser.email}"),
                            Text("Nome: ${loggedUser.nome}"),
                            Text("Cognome: ${loggedUser.cognome}"),
                            Text("Partita IVA: ${loggedUser.partitaiva}"),
                          ],
                        ),
                      ),
                      Divider(),
                      Padding(
                        padding: const EdgeInsets.symmetric(
                            horizontal: 16.0, vertical: 8.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              children: [
                                Icon(Icons.history_edu, color: Colors.green),
                                SizedBox(width: 8),
                                Text(
                                  "Biografia",
                                  style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 16),
                                ),
                              ],
                            ),
                            SizedBox(height: 8),
                            Text("${loggedUser.biografia}"),
                          ],
                        ),
                      ),
                      Divider(),
                      _buildTile(
                        icon: Icons.edit,
                        iconColor: Colors.blue,
                        text: 'Modifica Account',
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (_) => EditProfileAgentPage(),
                            ),
                          );
                        },
                      ),
                      Divider(),
                      _buildTile(
                        icon: Icons.lock_reset,
                        iconColor: Colors.amber,
                        text: 'Ripristina Password',
                        onTap: () {},
                      ),
                      Divider(),
                      ExpansionTile(
                        leading: Icon(Icons.notifications, color: Colors.green),
                        title: Text('Notifications'),
                        // trailing: Switch(
                        //   value: _notificationsEnabled,
                        //   onChanged: (v) => setState(() => _notificationsEnabled = v),
                        // ),
                        children: _notificationsEnabled
                            ? [
                                _buildSubSwitch(
                                  label: 'Notifiche Appuntamenti',
                                  value: loggedUser.notifyAppointment,
                                  onChanged: (v) => setState(
                                      () => loggedUser.notifyAppointment = v),
                                ),
                                Padding(
                                  padding: const EdgeInsets.only(
                                      left: 72.0, right: 16.0),
                                  child: ListTile(
                                    contentPadding: EdgeInsets.zero,
                                    title: Text("Alias Notifiche Appuntamenti"),
                                    // trailing: Switch(value: loggedUser.notify_appointment, onChanged: onChanged),
                                    // onTap: () => onChanged(!value),
                                  ),
                                ),
                              ]
                            : [],
                      ),
                      Divider(),
                      _buildTile(
                          icon: Icons.update,
                          iconColor: Colors.lightBlue,
                          text: 'Aggiorna Dati',
                          onTap: () {
                            _refresh();
                          }),
                      Divider(),
                      _buildTile(
                          icon: Icons.exit_to_app,
                          iconColor: Colors.red,
                          text: 'Esci',
                          onTap: () {
                            _showSignOutDialog(context);
                          }),
                      Divider(),
                    ],
                  ),
                ),
              ],
            ),
          ),
        );
    }
  }
}

Widget _buildSubSwitch({
  required String label,
  required bool value,
  required ValueChanged<bool> onChanged,
}) {
  return Padding(
    padding: const EdgeInsets.only(left: 72.0, right: 16.0),
    child: ListTile(
      contentPadding: EdgeInsets.zero,
      title: Text(label),
      trailing: Switch(value: value, onChanged: onChanged),
      onTap: () => onChanged(!value),
    ),
  );
}

Widget _buildTile({
  required IconData icon,
  required Color iconColor,
  required String text,
  required VoidCallback onTap,
}) {
  return ListTile(
    leading: Icon(icon, color: iconColor),
    title: Text(text),
    trailing: Icon(Icons.chevron_right),
    onTap: onTap,
  );
}

void _showSignOutDialog(BuildContext context) {
  showDialog(
    context: context,
    builder: (_) => AlertDialog(
      title: const Text("Conferma"),
      icon: const Icon(Icons.warning_rounded, color: Colors.red),
      content: const Text("Vuoi proseguire ad uscire?"),
      actions: [
        TextButton(
          child: const Text("No"),
          onPressed: () => Navigator.of(context).pop(),
        ),
        TextButton(
          child: const Text("Si"),
          onPressed: () {
            Navigator.of(context).pop();
            ProfileController.resetJWT();
            Navigator.of(context).pushNamed(RouteWindows.loginWindow);
          },
        ),
      ],
    ),
  );
}

// I/flutter (21651): │ 💡 {
// I/flutter (21651): │ 💡   "idUser": "5", OK
// I/flutter (21651): │ 💡   "nome": "carlo", OK
// I/flutter (21651): │ 💡   "email": "filix820zec@gmail.com", OK
// I/flutter (21651): │ 💡   "cognome": "vanzini", OK
// I/flutter (21651): │ 💡   "password": "$1$umHzr98T$3Vwn/QZppjsfu5XchOib.0", OK
// I/flutter (21651): │ 💡   "notifyAppointment": null, T
// I/flutter (21651): │ 💡   "idPushNotify": "", OK
// I/flutter (21651): │ 💡   "biografia": "\\x31", OK
// I/flutter (21651): │ 💡   "immagineprofile": null, 
// I/flutter (21651): │ 💡   "partitaiva": "11111111111",
// I/flutter (21651): │ 💡   "sensitivity": null,
// I/flutter (21651): │ 💡   "notify_appointment": true Y
// I/flutter (21651): │ 💡 }