import 'package:flutter/material.dart';

import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/ManagementAccount/ProfileController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';

class ProfileWindow extends StatefulWidget {
  @override
  State<ProfileWindow> createState() => _ProfileWindowState();
}

class _ProfileWindowState extends State<ProfileWindow> {
  bool _notificationsEnabled = true; // switch principale
  bool _emailNotif = true;
  bool _pushNotif = false;
  bool _smsNotif = false;

  Utente? profileBuff;
  @override
  void initState() {
    super.initState();
    // ProfileController().fetchProfile();
  }

  @override
  Widget build(BuildContext context) {
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
                              onChanged: (v) => setState(() => _emailNotif = v),
                            ),
                            _buildSubSwitch(
                              label: 'Notifiche Immobili',
                              value: _pushNotif,
                              onChanged: (v) => setState(() => _pushNotif = v),
                            ),
                            _buildSubSwitch(
                              label: 'Notifiche Cambiamento di prezzo',
                              value: _smsNotif,
                              onChanged: (v) => setState(() => _smsNotif = v),
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
}