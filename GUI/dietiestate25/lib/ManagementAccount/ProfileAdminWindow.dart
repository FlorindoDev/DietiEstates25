import 'package:flutter/material.dart';

import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';

import 'EditProfileWidow.dart';
import 'EditPasswordWidow.dart';

class ProfileAdminWindow extends StatefulWidget {
  @override
  State<ProfileAdminWindow> createState() => _ProfileAdminWindowState();
}

class _ProfileAdminWindowState extends State<ProfileAdminWindow> {
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
                              builder: (_) => EditProfilePage(),
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
                        text: 'Moddifica Password',
                        onTap: () {
                          Navigator.push<bool>(
                            context,
                            MaterialPageRoute(
                              builder: (_) => EditPasswordPage(),
                            ),
                          );
                        },
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