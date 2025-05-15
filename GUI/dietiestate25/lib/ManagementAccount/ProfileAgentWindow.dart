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
      Uint8List imgBytes = base64Decode(loggedUser.immagineprofile);
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
                child: Image.memory(imgBytes,
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
                            Text("Tipo Account: ${loggedUser.runtimeType}"),
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
                        onTap: () async {
                          final didUpdate = await Navigator.push<bool>(
                            context,
                            MaterialPageRoute(
                              builder: (_) => EditProfileAgentPage(),
                            ),
                          );
                          if (didUpdate == true) { // se true, ricarico il profilo
                            _refresh();
                          }
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
                                  onChanged: // (v) => setState(() => loggedUser.notifyAppointment = v),
                                  (bool newValue) async {
                                      final oldValue = loggedUser.notifyAppointment;
                                      // 1) aggiorno subito la UI
                                      setState(() => loggedUser.notifyAppointment = newValue);

                                      // 2) persisto su server
                                      final success = await ProfileController.updateProfile(loggedUser);

                                      if (!success) {
                                        // 3a) mostro un messaggio di errore
                                        ScaffoldMessenger.of(context).showSnackBar(
                                          SnackBar(content: Text('Impossibile aggiornare le notifiche'))
                                        );
                                        // 3b) ripristino lo stato precedente
                                        setState(() => loggedUser.notifyAppointment = oldValue);
                                      }
                                    },
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