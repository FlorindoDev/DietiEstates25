import 'package:dietiestate25/AgentHome/AgentHomeController.dart';
import 'package:dietiestate25/ManagementAccount/ProfileAgentWindow.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/AgentHome/NotificationAgentWindow.dart';
import 'package:dietiestate25/AgentHome/AgentAppointmentWindow.dart';
import 'package:dietiestate25/Booking/BookingWindow.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:convert';
import 'dart:typed_data';

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
  List<String> _imagesBase64 = [];

  Future<void> _pickImage() async {
    final XFile? file = await _picker.pickImage(source: ImageSource.gallery);
    if (file == null) return;
    final bytes = await file.readAsBytes();
    final newBase64 = base64Encode(bytes);

    setState(() {
      _imagesBase64.add(newBase64);
    });

    print(_imagesBase64);
  }

  void _removeImage(int index) {
    setState(() {
      _imagesBase64.removeAt(index);
    });
  }

  final ImagePicker _picker = ImagePicker();

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
                GestureDetector(
                  onTap: _pickImage,
                  child: Container(
                    width: 96,
                    height: 96,
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      color: Colors.grey.shade200, // sfondo leggero
                      // border: Border.all(color: Colors.grey.shade400, width: 2),
                    ),
                    child: Center(
                      child: Icon(
                        Icons.camera_alt, // icona desiderata
                        size: 40,
                        color: Colors.grey.shade600,
                      ),
                    ),
                  ),
                ),
                const SizedBox(height: 16),
                Wrap(
                  spacing: 8,
                  runSpacing: 8,
                  children: List.generate(_imagesBase64.length, (index) {
                    final imageBytes = base64Decode(_imagesBase64[index]);
                    return GestureDetector(
                      onTap: () => _removeImage(index),
                      child: Stack(
                        alignment: Alignment.topRight,
                        children: [
                          ClipRRect(
                            borderRadius: BorderRadius.circular(8),
                            child: Image.memory(
                              imageBytes,
                              width: 80,
                              height: 80,
                              fit: BoxFit.cover,
                            ),
                          ),
                          Container(
                            decoration: BoxDecoration(
                              color: Colors.black54,
                              shape: BoxShape.circle,
                            ),
                            child: const Icon(
                              Icons.close,
                              color: Colors.white,
                              size: 20,
                            ),
                          ),
                        ],
                      ),
                    );
                  }),
                ),
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
                          foto: _imagesBase64,
                          context: context);
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
