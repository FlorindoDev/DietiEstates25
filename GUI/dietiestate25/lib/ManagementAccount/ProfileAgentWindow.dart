import 'package:flutter/material.dart';
import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';

import 'package:dietiestate25/Model/Utente/Utente.dart';

class ProfileAgentWindow extends StatefulWidget {
  @override
  State<ProfileAgentWindow> createState() => _ProfileAgentWindowState();
}

enum LoadState { loading, success, error }

class _ProfileAgentWindowState extends State<ProfileAgentWindow> {
  bool _notificationsEnabled = true; // switch principale
  bool _emailNotif = true;
  bool _pushNotif = false;
  bool _smsNotif = false;

  Utente? profileBuff;
  LoadState _loadState = LoadState.loading;
  @override
  void initState() {
    super.initState();
    // state = await ProfileController().fetchProfile();
    _loadProfile();
  }

  Future<void> _loadProfile() async {
    bool exitState = await ProfileController().fetchProfile();
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
                ElevatedButton(
                  onPressed: () {
                    setState(() {
                      _loadState = LoadState.loading;
                    });
                    _loadProfile();
                  },
                  child: Text("Riprova"),
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
                const SizedBox(height: 24),
                CircleAvatar(
                  radius: 48,
                  backgroundColor: Colors.grey.shade300,
                  child: Icon(Icons.person, size: 48, color: Colors.white),
                ),
                const SizedBox(height: 16),
                Expanded(
                  child: ListView(
                    children: [
                      ListTile(
                        leading: Icon(Icons.history_edu, color: Colors.green),
                        title: Text("Sono Domenico e agg perz a cap"),
                      ),
                      Divider(),
                      ListTile(
                        leading: Icon(Icons.info, color: Colors.red),
                        title: Text("Tipo Account: Agente"),
                      ),
                      Divider(),
                      _buildTile(
                        icon: Icons.edit,
                        iconColor: Colors.blue,
                        text: 'Modifica Generalità',
                        onTap: () {},
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
                                  value: _emailNotif,
                                  onChanged: (v) =>
                                      setState(() => _emailNotif = v),
                                ),
                                _buildSubSwitch(
                                  label: 'Notifiche Immobili',
                                  value: _pushNotif,
                                  onChanged: (v) =>
                                      setState(() => _pushNotif = v),
                                ),
                                _buildSubSwitch(
                                  label: 'Notifiche Cambiamento di prezzo',
                                  value: _smsNotif,
                                  onChanged: (v) =>
                                      setState(() => _smsNotif = v),
                                ),
                              ]
                            : [],
                      ),
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
