import 'package:dietiestate25/AgentHome/AgentHomeController.dart';
import 'package:dietiestate25/ManagementAccount/ProfileAgentWindow.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/AgentHome/NotificationAgentWindow.dart';
import 'package:dietiestate25/AgentHome/AgentAppointmentWindow.dart';
import 'package:dietiestate25/Booking/BookingWindow.dart';

class AgentHomeWindow extends StatefulWidget {
  const AgentHomeWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<AgentHomeWindow> createState() => _AgentHomeWindowState();
}

class _AgentHomeWindowState extends State<AgentHomeWindow> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    const HomeScreen(),
    NotificationAgentWindow(),
    AgentAppointmentWindow(),
    ProfileAgentWindow(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: _pages[_selectedIndex],
      bottomNavigationBar: NavigationBar(
        selectedIndex: _selectedIndex,
        onDestinationSelected: (index) =>
            setState(() => _selectedIndex = index),
        destinations: const [
          NavigationDestination(
            icon: Icon(Icons.house_rounded, color: Colors.white),
            label: 'Home',
          ),
          NavigationDestination(
            icon: Icon(Icons.notifications, color: Colors.white),
            label: 'Notifiche',
          ),
          NavigationDestination(
            icon: Icon(Icons.calendar_month, color: Colors.white),
            label: 'Appuntamenti',
          ),
          NavigationDestination(
            icon: Icon(Icons.person, color: Colors.white),
            label: 'Profilo',
          ),
        ],
        indicatorColor: MyApp.celeste,
        backgroundColor: MyApp.blu,
        overlayColor: WidgetStateProperty.all(Colors.transparent),
        labelTextStyle: WidgetStateProperty.resolveWith<TextStyle>(
          (Set<WidgetState> states) {
            if (states.contains(WidgetState.selected)) {
              return const TextStyle(
                  color: Colors.white, fontWeight: FontWeight.bold);
            }
            return const TextStyle(color: Colors.white70);
          },
        ),
      ),
    );
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreen();
}

class _HomeScreen extends State<HomeScreen> {
  late Future<List<Estate>> estates;

  final _formKey = GlobalKey<FormState>();

  // Estate fields
  String descrizione = '';
  double price = 0.0;
  double space = 0.0;
  int rooms = 0;
  int wc = 0;
  int floor = 0;
  int garage = 0;
  bool elevator = false;
  String agenzia = '';
  String agente = '';
  String stato = 'New';
  String mode = 'Affitto';
  String classeEnergetica = 'A';

  // Indirizzo fields
  String statoIndirizzo = '';
  String citta = '';
  String? quartiere;
  String via = '';
  String numeroCivico = '';
  String cap = '';
  double latitudine = 0.0;
  double logitudine = 0.0;

  @override
  void initState() {
    super.initState();
    estates = AgentHomeController.getEstate(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: SingleChildScrollView(
          child: Form(
            key: _formKey,
            child: Column(
              children: [
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Descrizione'),
                  onSaved: (value) => descrizione = value ?? '',
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Prezzo'),
                  keyboardType: TextInputType.number,
                  onSaved: (value) =>
                      price = double.tryParse(value ?? '0') ?? 0,
                ),
                TextFormField(
                  decoration:
                      const InputDecoration(labelText: 'Dimensione (m2)'),
                  keyboardType: TextInputType.number,
                  onSaved: (value) =>
                      space = double.tryParse(value ?? '0') ?? 0,
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Locali'),
                  keyboardType: TextInputType.number,
                  onSaved: (value) => rooms = int.tryParse(value ?? '0') ?? 0,
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Bagni'),
                  keyboardType: TextInputType.number,
                  onSaved: (value) => wc = int.tryParse(value ?? '0') ?? 0,
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Piano'),
                  keyboardType: TextInputType.number,
                  onSaved: (value) => floor = int.tryParse(value ?? '0') ?? 0,
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Garage'),
                  keyboardType: TextInputType.number,
                  onSaved: (value) => garage = int.tryParse(value ?? '0') ?? 0,
                ),
                SwitchListTile(
                  title: const Text('Ascensore'),
                  value: elevator,
                  onChanged: (val) => setState(() => elevator = val),
                ),
                DropdownButtonFormField<String>(
                  decoration: const InputDecoration(labelText: 'Stato'),
                  value: stato = "Nuovo",
                  items: ["Nuovo", "Ottimo", "Buono", "Da ristrutturare"]
                      .map((val) =>
                          DropdownMenuItem(value: val, child: Text(val)))
                      .toList(),
                  onChanged: (val) => setState(() => stato = val!),
                ),
                DropdownButtonFormField<String>(
                  decoration: const InputDecoration(labelText: 'Contratto'),
                  value: mode,
                  items: ['Affitto', 'Vendita']
                      .map((val) =>
                          DropdownMenuItem(value: val, child: Text(val)))
                      .toList(),
                  onChanged: (val) => setState(() => mode = val!),
                ),
                DropdownButtonFormField<String>(
                  decoration:
                      const InputDecoration(labelText: 'Classe Energetica'),
                  value: classeEnergetica,
                  items: ['A', 'B', 'C', 'D', 'E', 'F', 'G']
                      .map((val) =>
                          DropdownMenuItem(value: val, child: Text(val)))
                      .toList(),
                  onChanged: (val) => setState(() => classeEnergetica = val!),
                ),
                const Divider(),
                const Text("Indirizzo",
                    style: TextStyle(fontWeight: FontWeight.bold)),
                TextFormField(
                  decoration:
                      const InputDecoration(labelText: 'Stato (Indirizzo)'),
                  onSaved: (value) => statoIndirizzo = value ?? '',
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Città'),
                  onSaved: (value) => citta = value ?? '',
                ),
                TextFormField(
                  decoration:
                      const InputDecoration(labelText: 'Quartiere (opzionale)'),
                  onSaved: (value) => quartiere = value,
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Via'),
                  onSaved: (value) => via = value ?? '',
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Numero Civico'),
                  onSaved: (value) => numeroCivico = value ?? '',
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'CAP'),
                  onSaved: (value) => cap = value ?? '',
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Latitudine'),
                  keyboardType: TextInputType.number,
                  onSaved: (value) =>
                      latitudine = double.tryParse(value ?? '0') ?? 0.0,
                ),
                TextFormField(
                  decoration: const InputDecoration(labelText: 'Longitudine'),
                  keyboardType: TextInputType.number,
                  onSaved: (value) =>
                      logitudine = double.tryParse(value ?? '0') ?? 0.0,
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      _formKey.currentState!.save();
                      AgentHomeController.createEstate(
                        descrizione: descrizione,
                        price: price,
                        space: space,
                        rooms: rooms,
                        wc: wc,
                        floor: floor,
                        garage: garage,
                        elevator: elevator,
                        stato: stato,
                        mode: mode,
                        classeEnergetica: classeEnergetica,
                        statoIndirizzo: statoIndirizzo,
                        citta: citta,
                        quartiere: quartiere,
                        via: via,
                        numeroCivico: numeroCivico,
                        cap: cap,
                        latitudine: latitudine,
                        logitudine: logitudine,
                      );
                    }
                  },
                  child: const Text('Salva'),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
