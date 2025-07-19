import 'package:dietiestate25/AgentHome/AgentHomeController.dart';
import 'package:dietiestate25/ManagementAccount/ProfileAgentWindow.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/main.dart';
import 'package:dietiestate25/AgentHome/NotificationAgentWindow.dart';
import 'package:dietiestate25/AgentHome/AgentAppointmentWindow.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:convert';

import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';
import 'package:geolocator/geolocator.dart';

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
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final ImagePicker _picker = ImagePicker();
  List<String> _imagesBase64 = [];
  bool _isLoadingLocation = true;

  // Estate fields
  final _formKey = GlobalKey<FormState>();
  String descrizione = '';
  double price = 0;
  double space = 0;
  int rooms = 0;
  int wc = 0;
  int floor = 0;
  int garage = 0;
  bool elevator = false;
  String stato = 'Nuovo';
  String mode = 'Affitto';
  String classeEnergetica = 'A';

  // Address fields
  String statoIndirizzo = '';
  String citta = '';
  String? quartiere;
  String via = '';
  String numeroCivico = '';
  String cap = '';
  double latitudine = 40.88333000;
  double logitudine = 14.41667000;

  late Future<List<Estate>> estates;
  final MapController _mapController = MapController();

  @override
  void initState() {
    super.initState();
    estates = AgentHomeController.getEstate(context);
    _getCurrentLocation();
  }

  Future<void> _getCurrentLocation() async {
    LocationPermission permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
    }
    if (permission == LocationPermission.whileInUse ||
        permission == LocationPermission.always) {
      Position position = await Geolocator.getCurrentPosition(
          desiredAccuracy: LocationAccuracy.high);
      setState(() {
        latitudine = position.latitude;
        logitudine = position.longitude;
        _isLoadingLocation = false;
      });
    } else {
      setState(() => _isLoadingLocation = false);
    }
  }

  void _onMapTap(TapPosition _, LatLng point) {
    setState(() {
      latitudine = point.latitude;
      logitudine = point.longitude;
    });
  }

  Future<void> _pickImage() async {
    final XFile? file = await _picker.pickImage(source: ImageSource.gallery);
    if (file == null) return;
    final bytes = await file.readAsBytes();
    setState(() => _imagesBase64.add(base64Encode(bytes)));
  }

  void _removeImage(int index) {
    setState(() => _imagesBase64.removeAt(index));
  }

  bool _containsNumber(String value) {
    return RegExp(r'[0-9]').hasMatch(value);
  }

  void _onSavePressed() {
    if (!_formKey.currentState!.validate()) return;
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
      context: context,
    );
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16),
      child: Form(
        key: _formKey,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Center(
              child: GestureDetector(
                onTap: _pickImage,
                child: CircleAvatar(
                  radius: 48,
                  backgroundColor: Colors.grey.shade200,
                  child: Icon(Icons.camera_alt,
                      size: 40, color: Colors.grey.shade600),
                ),
              ),
            ),
            const SizedBox(height: 12),
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: List.generate(_imagesBase64.length, (i) {
                final bytes = base64Decode(_imagesBase64[i]);
                return GestureDetector(
                  onTap: () => _removeImage(i),
                  child: Stack(
                    children: [
                      ClipRRect(
                        borderRadius: BorderRadius.circular(8),
                        child: Image.memory(bytes,
                            width: 80, height: 80, fit: BoxFit.cover),
                      ),
                      Positioned(
                        right: 0,
                        child: Container(
                          decoration: BoxDecoration(
                              color: Colors.black54, shape: BoxShape.circle),
                          child: const Icon(Icons.close,
                              size: 20, color: Colors.white),
                        ),
                      ),
                    ],
                  ),
                );
              }),
            ),
            const SizedBox(height: 16),
            TextFormField(
              decoration: const InputDecoration(labelText: 'Descrizione'),
              validator: (v) =>
                  (v == null || v.trim().isEmpty) ? 'Campo obbligatorio' : null,
              onSaved: (v) => descrizione = v ?? '',
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    decoration: const InputDecoration(labelText: 'Prezzo'),
                    keyboardType: TextInputType.number,
                    validator: (v) {
                      if (v == null || v.isEmpty) return 'Campo obbligatorio';
                      final val = double.tryParse(v);
                      if (val == null || val <= 0)
                        return 'Inserisci un numero valido';
                      return null;
                    },
                    onSaved: (v) => price = double.tryParse(v ?? '0') ?? 0,
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: TextFormField(
                    decoration:
                        const InputDecoration(labelText: 'Dimensione (m²)'),
                    keyboardType: TextInputType.number,
                    validator: (v) {
                      if (v == null || v.isEmpty) return 'Campo obbligatorio';
                      final val = double.tryParse(v);
                      if (val == null || val <= 0)
                        return 'Inserisci un numero valido';
                      return null;
                    },
                    onSaved: (v) => space = double.tryParse(v ?? '0') ?? 0,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    decoration: const InputDecoration(labelText: 'Locali'),
                    keyboardType: TextInputType.number,
                    validator: (v) {
                      if (v == null || v.isEmpty) return 'Campo obbligatorio';
                      final val = int.tryParse(v);
                      if (val == null || val <= 0) return 'Numero non valido';
                      return null;
                    },
                    onSaved: (v) => rooms = int.tryParse(v ?? '0') ?? 0,
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: TextFormField(
                    decoration: const InputDecoration(labelText: 'Bagni'),
                    keyboardType: TextInputType.number,
                    validator: (v) {
                      if (v == null || v.isEmpty) return 'Campo obbligatorio';
                      final val = int.tryParse(v);
                      if (val == null || val <= 0) return 'Numero non valido';
                      return null;
                    },
                    onSaved: (v) => wc = int.tryParse(v ?? '0') ?? 0,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                Expanded(
                  child: SwitchListTile(
                    title: const Text('Ascensore'),
                    value: elevator,
                    onChanged: (v) => setState(() => elevator = v),
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: TextFormField(
                    decoration: const InputDecoration(labelText: 'Garage'),
                    keyboardType: TextInputType.number,
                    validator: (v) {
                      if (v == null || v.isEmpty) return 'Campo obbligatorio';
                      final val = int.tryParse(v);
                      if (val == null || val < 0) return 'Numero non valido';
                      return null;
                    },
                    onSaved: (v) => garage = int.tryParse(v ?? '0') ?? 0,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            DropdownButtonFormField<String>(
              decoration: const InputDecoration(labelText: 'Stato'),
              value: stato,
              items: ['Nuovo', 'Ottimo', 'Buono', 'Da ristrutturare']
                  .map((e) => DropdownMenuItem(value: e, child: Text(e)))
                  .toList(),
              onChanged: (v) => stato = v!,
              validator: (v) =>
                  (v == null || v.isEmpty) ? 'Campo obbligatorio' : null,
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                Expanded(
                  child: DropdownButtonFormField<String>(
                    decoration: const InputDecoration(labelText: 'Contratto'),
                    value: mode,
                    items: ['Affitto', 'Vendita']
                        .map((e) => DropdownMenuItem(value: e, child: Text(e)))
                        .toList(),
                    onChanged: (v) => mode = v!,
                    validator: (v) =>
                        (v == null || v.isEmpty) ? 'Campo obbligatorio' : null,
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: DropdownButtonFormField<String>(
                    decoration:
                        const InputDecoration(labelText: 'Classe Energetica'),
                    value: classeEnergetica,
                    items: ['A', 'B', 'C', 'D', 'E', 'F', 'G']
                        .map((e) => DropdownMenuItem(value: e, child: Text(e)))
                        .toList(),
                    onChanged: (v) => classeEnergetica = v!,
                    validator: (v) =>
                        (v == null || v.isEmpty) ? 'Campo obbligatorio' : null,
                  ),
                ),
              ],
            ),
            const Divider(height: 32),
            const Text('Indirizzo',
                style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 12),
            TextFormField(
              decoration: const InputDecoration(labelText: 'Piano'),
              keyboardType: TextInputType.number,
              validator: (v) {
                if (v == null || v.isEmpty) return 'Campo obbligatorio';
                final val = int.tryParse(v);
                if (val == null || val < 0) return 'Numero non valido';
                return null;
              },
              onSaved: (v) => floor = int.tryParse(v ?? '0') ?? 0,
            ),
            TextFormField(
              decoration: const InputDecoration(labelText: 'Stato (Indirizzo)'),
              validator: (v) {
                if (v == null || v.trim().isEmpty) return 'Campo obbligatorio';
                if (_containsNumber(v)) return 'Non può contenere numeri';
                return null;
              },
              onSaved: (v) => statoIndirizzo = v ?? '',
            ),
            const SizedBox(height: 12),
            TextFormField(
              decoration: const InputDecoration(labelText: 'Città'),
              validator: (v) {
                if (v == null || v.trim().isEmpty) return 'Campo obbligatorio';
                if (_containsNumber(v)) return 'Non può contenere numeri';
                return null;
              },
              onSaved: (v) => citta = v ?? '',
            ),
            const SizedBox(height: 12),
            TextFormField(
              decoration:
                  const InputDecoration(labelText: 'Quartiere (opzionale)'),
              onSaved: (v) => quartiere = v,
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    decoration: const InputDecoration(labelText: 'Via'),
                    validator: (v) {
                      if (v == null || v.trim().isEmpty)
                        return 'Campo obbligatorio';
                      if (_containsNumber(v)) return 'Non può contenere numeri';
                      return null;
                    },
                    onSaved: (v) => via = v ?? '',
                  ),
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: TextFormField(
                    decoration:
                        const InputDecoration(labelText: 'Numero Civico'),
                    keyboardType: TextInputType.text,
                    validator: (v) => (v == null || v.trim().isEmpty)
                        ? 'Campo obbligatorio'
                        : null,
                    onSaved: (v) => numeroCivico = v ?? '',
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    decoration: const InputDecoration(labelText: 'CAP'),
                    keyboardType: TextInputType.number,
                    validator: (v) {
                      if (v == null || v.isEmpty) return 'Campo obbligatorio';
                      final val = int.tryParse(v);
                      if (val == null || val <= 0) return 'Numero non valido';
                      return null;
                    },
                    onSaved: (v) => cap = v ?? '',
                  ),
                ),
              ],
            ),
            const SizedBox(height: 24),
            if (_isLoadingLocation)
              const Center(child: CircularProgressIndicator())
            else
              Container(
                height: 250,
                margin: const EdgeInsets.only(bottom: 24),
                child: FlutterMap(
                  mapController: _mapController,
                  options: MapOptions(
                      initialZoom: 5,
                      initialCenter: LatLng(latitudine, logitudine),
                      onTap: _onMapTap),
                  children: [
                    TileLayer(
                      urlTemplate:
                          'https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}.png',
                      subdomains: ['a', 'b', 'c', 'd'],
                      userAgentPackageName: 'com.example.dietiestate25',
                    ),
                    MarkerLayer(
                      markers: [
                        Marker(
                          point: LatLng(latitudine, logitudine),
                          width: 40,
                          height: 40,
                          child: Icon(
                            Icons.location_pin,
                            size: 40,
                            color: MyApp.rosso,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            Center(
              child: ElevatedButton(
                onPressed: _onSavePressed,
                child: const Text('Salva'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
