import 'dart:ffi';
import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Search/SearchController.dart'
    as my_search_controller;
import 'package:latlong2/latlong.dart';
import 'package:numberpicker/numberpicker.dart';

class SearchFilterWindow extends StatefulWidget {
  SearchFilterWindow(
      {super.key, required this.appbar, required this.citta});
  AppBar appbar;
  String citta;
  static LatLng? currentPosition;
  static double? searchRadius;

  @override
  State<SearchFilterWindow> createState() => _SearchFilterWindowState();
}

class _SearchFilterWindowState extends State<SearchFilterWindow> {
  bool isLoading = false;
  String mode = "Affitto";
  int minPrice = 0;
  int maxPrice = 0;
  int minDimensione = 0;
  int maxDimensione = 0;
  int minStanze = 0;
  int maxStanze = 0;
  int bagni = 0;
  String ascensore = "Tutto";
  String stato = "Tutto";
  int garage = 0;
  final List<String> opzioni = [
    'Tutto',
    'A',
    'A2',
    'A3',
    'A4',
    'B',
    'C',
    'D',
    'E',
    'F'
  ];
  double _sliderIndex = 0;

    void azzera() {
      minPrice = 0;
      maxPrice = 0;
      minDimensione = 0;
      maxDimensione = 0;
      minStanze = 0;
      maxStanze = 0;
      bagni = 0;
      ascensore = "Tutto";
      stato = "Tutto";
      garage = 0;
      _sliderIndex = 0;
      widget.citta = "";
      SearchFilterWindow.currentPosition = null;
      SearchFilterWindow.searchRadius = null;

  



  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(12.0),
          child: Column(
            children: [
               if (isLoading)
                    const Center(
                      child: Padding(
                        padding: EdgeInsets.all(20),
                        child: CircularProgressIndicator(strokeWidth: 5),
                      ),
                    ),
              /// Bottone Cerca
              ElevatedButton(
                
                onPressed: () async{
                  setState(() {
                    isLoading = true;
                  });
                  await my_search_controller.SearchController.filteredSearch(
                      context,
                      1,
                      mode,
                      minPrice,
                      maxPrice,
                      minDimensione,
                      maxDimensione,
                      minStanze,
                      maxStanze,
                      bagni,
                      ascensore,
                      stato,
                      garage,
                      opzioni[_sliderIndex.round()],
                      widget.citta,
                      SearchFilterWindow.currentPosition?.latitude,
                      SearchFilterWindow.currentPosition?.longitude,
                      SearchFilterWindow.searchRadius
                    );
                      azzera();
                    setState(() {
                      isLoading = false;
                    });
                },
                style: ButtonStyle(
                  padding: WidgetStateProperty.all(
                      const EdgeInsets.symmetric(vertical: 15, horizontal: 30)),
                  backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                  foregroundColor: WidgetStateProperty.all(Colors.white),
                  shape: WidgetStateProperty.all(
                    RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(32)),
                  ),
                ),
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: const [
                    Icon(Icons.search,
                        color: Colors.white, size: 50),
                    SizedBox(width: 10),
                    Text("Cerca", style: TextStyle(fontSize: 25)),
                  ],
                ),
              ),
              const SizedBox(height: 20),
              /// Compra / Affitto
              const Divider(color: Colors.grey, thickness: 1),
              Text("Modalitá", style: const TextStyle(fontSize: 24)),
              Wrap(
                spacing: 10,
                alignment: WrapAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: mode == "Compra"
                        ? null
                        : () {
                            setState(() {
                              mode = "Compra";
                            });
                          },
                    style: mode == "Compra"
                        ? AccessController.not_clickable_style_button
                        : AccessController.clickable_style_button,
                    child: const Text('Compra'),
                  ),
                  ElevatedButton(
                    onPressed: mode == "Affitto"
                        ? null
                        : () {
                            setState(() {
                              mode = "Affitto";
                            });
                          },
                    style: mode == "Affitto"
                        ? AccessController.not_clickable_style_button
                        : AccessController.clickable_style_button,
                    child: const Text('Affitto'),
                  ),
                ],
              ),

              const Divider(color: Colors.grey, thickness: 1),

              //Prezzo
              _buildDualNumberPicker("Prezzo", "Min", "Max", minPrice, maxPrice,
                  min: 0,
                  max: mode == "Compra" ? 5000000 : 8000,
                  step: mode == "Compra" ? 10000 : 50,
                  onMinChanged: (v) => setState(() => minPrice = v),
                  onMaxChanged: (v) => setState(() => maxPrice = v)),

              const Divider(color: Colors.grey, thickness: 1),

              //Superfice
              _buildDualNumberPicker(
                  "Superfice", "Min", "Max", minDimensione, maxDimensione,
                  min: 0,
                  max: 1000,
                  step: 20,
                  onMinChanged: (v) => setState(() => minDimensione = v),
                  onMaxChanged: (v) => setState(() => maxDimensione = v)),

              const Divider(color: Colors.grey, thickness: 1),

              //Locali
              _buildDualNumberPicker(
                  "Locali", "Min", "Max", minStanze, maxStanze,
                  min: 0,
                  max: 100,
                  step: 1,
                  onMinChanged: (v) => setState(() => minStanze = v),
                  onMaxChanged: (v) => setState(() => maxStanze = v)),

              const Divider(color: Colors.grey, thickness: 1),

              //Bagni
              _buildSingleNumberPicker("Bagni", bagni,
                  max: 10, onChanged: (v) => setState(() => bagni = v)),

              const Divider(color: Colors.grey, thickness: 1),

              //Ascensore
              _buildButtonGroup("Ascensore", ["Tutto", "Si", "No"], ascensore,
                  (value) => setState(() => ascensore = value)),

              const Divider(color: Colors.grey, thickness: 1),

              //Stato Immobile
              _buildButtonGroup(
                  "Stato Immobile",
                  ["Tutto", "Nuovo", "Ottimo", "Buono", "Da ristrutturare"],
                  stato,
                  (value) => setState(() => stato = value)),

              const Divider(color: Colors.grey, thickness: 1),

              //Garage
              _buildSingleNumberPicker("Garage", garage,
                  max: 10, onChanged: (v) => setState(() => garage = v)),

              const Divider(color: Colors.grey, thickness: 1),

              //Classe Energetica
              Column(
                children: [
                  const Text("Classe Energetica",
                      style: TextStyle(fontSize: 24)),
                  Slider(
                    value: _sliderIndex,
                    min: 0,
                    max: (opzioni.length - 1).toDouble(),
                    divisions: opzioni.length - 1,
                    label: opzioni[_sliderIndex.round()],
                    onChanged: (newIndex) {
                      setState(() {
                        _sliderIndex = newIndex;
                      });
                    },
                  ),
                ],
              ),

              const SizedBox(height: 20),

              

              
            ],
          ),
        ),
      ),
    );
  }

  /// Widget due NumberPicker affiancati
  Widget _buildDualNumberPicker(
    String title,
    String labelMin,
    String labelMax,
    int valueMin,
    int valueMax, {
    required int min,
    required int max,
    required int step,
    required ValueChanged<int> onMinChanged,
    required ValueChanged<int> onMaxChanged,
  }) {
    return Column(
      children: [
        Text(title, style: const TextStyle(fontSize: 24)),
        SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: Row(
            children: [
              Column(
                children: [
                  Text(labelMin, style: const TextStyle(fontSize: 16)),
                  NumberPicker(
                    itemCount: 3,
                    itemWidth: 100,
                    itemHeight: 30,
                    value: valueMin,
                    minValue: min,
                    maxValue: max,
                    step: step,
                    onChanged: onMinChanged,
                  ),
                ],
              ),
              const SizedBox(width: 20),
              Column(
                children: [
                  Text(labelMax, style: const TextStyle(fontSize: 16)),
                  NumberPicker(
                    itemCount: 3,
                    itemWidth: 100,
                    itemHeight: 30,
                    value: valueMax,
                    minValue: min,
                    maxValue: max,
                    step: step,
                    onChanged: onMaxChanged,
                  ),
                ],
              ),
            ],
          ),
        )
      ],
    );
  }

  /// Widget helper per un singolo NumberPicker
  Widget _buildSingleNumberPicker(String title, int value,
      {required int max, required ValueChanged<int> onChanged}) {
    return Column(
      children: [
        Text(title, style: const TextStyle(fontSize: 24)),
        NumberPicker(
          itemCount: 3,
          itemWidth: 100,
          itemHeight: 30,
          value: value,
          minValue: 0,
          maxValue: max,
          step: 1,
          onChanged: onChanged,
        ),
      ],
    );
  }

  // per gruppi di bottoni
  Widget _buildButtonGroup(String title, List<String> options,
      String currentValue, ValueChanged<String> onSelected) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Text(title, style: const TextStyle(fontSize: 24)),
        Wrap(
          alignment: WrapAlignment.center,
          spacing: 8,
          runSpacing: 8,
          children: options.map((option) {
            return ElevatedButton(
              onPressed:
                  currentValue == option ? null : () => onSelected(option),
              style: currentValue == option
                  ? AccessController.not_clickable_style_button
                  : AccessController.clickable_style_button,
              child: Text(option),
            );
          }).toList(),
        ),
      ],
    );
  }
}
