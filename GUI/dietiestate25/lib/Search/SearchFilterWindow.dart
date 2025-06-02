import 'dart:ffi';

import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Search/SearchController.dart' as my_search_controller;
import 'package:numberpicker/numberpicker.dart';

class SearchFilterWindow extends StatefulWidget {
  const SearchFilterWindow({super.key, required this.appbar, required this.citta});
  final AppBar appbar;
  final String citta;

  @override
  State<SearchFilterWindow> createState() => _SearchFilterWindowState();
}

class _SearchFilterWindowState extends State<SearchFilterWindow> {
  String mode = "Tutto";
  int minPrice = 0;
  int maxPrice = 0;
  int minDimensione= 0;
  int maxDimensione = 0;
  int minStanze = 0;
  int maxStanze = 0;
  int bagni = 0;
  String ascensore = "Tutto";
  String stato = "Tutto";
  int garage = 0;
  final List<String> opzioni = ['Tutto', 'A', 'A2', 'A3', 'A4', 'B', 'C', 'D', 'E', 'F'];
  double _sliderIndex = 0;

  @override
  Widget build(BuildContext context) {
    
    

    return Scaffold(
      appBar: widget.appbar,
      body: SingleChildScrollView(

        child:  Column(
          
    
        spacing: 20,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            spacing: 10,
            children: [
              ElevatedButton(
                onPressed: mode == "Tutto"? null: () { setState(() {mode = "Tutto"; print(mode);}); },
                style: mode == "Tutto"? AccessController.not_clickable_style_button : AccessController.clickable_style_button,
                child: const Text('Tutto'),
              ),
              ElevatedButton(
                onPressed: mode == "Vendita"? null: () { setState(() {mode = "Vendita"; print(mode);}); },
                style: mode == "Vendita"? AccessController.not_clickable_style_button : AccessController.clickable_style_button,
                child: const Text('Vendita'),
              ),
    
              ElevatedButton(
                onPressed: mode == "Affitto"? null:() { setState(() {mode = "Affitto";print(mode);}); },
                style: mode == "Affitto"? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                child: const Text('Affitto'),
              ),
          

            ],
          ),
          Divider(
            color: Colors.grey,
            thickness: 1,
            height: 1, // spazio sopra e sotto
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            spacing: 5,
            children: [
              Text("Prezzo",style: TextStyle(fontSize: 24),),  
              Column(
                children: [
                  Text("Min",style: TextStyle(fontSize: 16),),
                  NumberPicker(
                    itemCount: 3,
                    itemWidth: 200,
                    itemHeight: 30,
                    value: minPrice,
                    minValue: 0,
                    maxValue: 1 << 30,
                    step: 10000,
                    onChanged: (value) => setState(() => minPrice = value),
              
                  ),  
                ],
              ),
              Column(
                children: [
                  Text("Max",style: TextStyle(fontSize: 16),),
                  NumberPicker(
                    itemCount: 3,
                    itemWidth: 200,
                    itemHeight: 30,
                    value: maxPrice,
                    minValue: 0,
                    maxValue: 1 << 30,
                    step: 10000,
                    onChanged: (value) => setState(() => maxPrice = value),
                  ),  
                ],
              ),    
              
              
            ],
          ),
          Divider(
            color: Colors.grey,
            thickness: 1,
            height: 1, // spazio sopra e sotto
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            spacing: 5,
            children: [
              Text("Superfice",style: TextStyle(fontSize: 24),),  
              Column(
                children: [
                  Text("Min",style: TextStyle(fontSize: 16),),
                  NumberPicker(
                    itemCount: 3,
                    itemWidth: 200,
                    itemHeight: 30,
                    value: minDimensione,
                    minValue: 0,
                    maxValue: 1 << 30,
                    step: 1,
                    onChanged: (value) => setState(() => minDimensione = value),
                    
                  ),  
                ],
              ),
              Column(
                children: [
                  Text("Max",style: TextStyle(fontSize: 16),),
                  NumberPicker(
                    itemCount: 3,
                    itemWidth: 200,
                    itemHeight: 30,
                    value: maxDimensione,
                    minValue: 0,
                    maxValue: 1 << 30,
                    step: 1,
                    onChanged: (value) => setState(() => maxDimensione = value),
                  ),  
                ],
              ),    
              
              
            ],
          ),
          Divider(
            color: Colors.grey,
            thickness: 1,
            height: 1, // spazio sopra e sotto
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            spacing: 5,
            children: [
              Text("Locali",style: TextStyle(fontSize: 24),),  
              Column(
                children: [
                  Text("Min",style: TextStyle(fontSize: 16),),
                  NumberPicker(
                    itemCount: 3,
                    itemWidth: 200,
                    itemHeight: 30,
                    value: minStanze,
                    minValue: 0,
                    maxValue: 1 << 30,
                    step: 1,
                    onChanged: (value) => setState(() => minStanze = value),
                    
                  ),  
                ],
              ),
              Column(
                children: [
                  Text("Max",style: TextStyle(fontSize: 16),),
                  NumberPicker(
                    itemCount: 3,
                    itemWidth: 200,
                    itemHeight: 30,
                    value: maxStanze,
                    minValue: 0,
                    maxValue: 1 << 30,
                    step: 1,
                    onChanged: (value) => setState(() => maxStanze = value),
                  ),  
                ],
              ),    
              
              
            ],
          ),
          Divider(
            color: Colors.grey,
            thickness: 1,
            height: 1, // spazio sopra e sotto
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            spacing: 5,
            children: [
              Text("Bagni",style: TextStyle(fontSize: 24),),  
              NumberPicker(
                    itemCount: 3,
                    itemWidth: 200,
                    itemHeight: 30,
                    value: bagni,
                    minValue: 0,
                    maxValue: 1 << 10,
                    step: 1,
                    onChanged: (value) => setState(() => bagni = value),
               ),  
              
              
            ],
          ),
          Divider(
            color: Colors.grey,
            thickness: 1,
            height: 1, // spazio sopra e sotto
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            spacing: 100,
            children: [
              Text("Ascensore",style: TextStyle(fontSize: 24),), 
              Row(
                children: [
                  ElevatedButton(
                    onPressed: ascensore == "Tutto"? null:() { setState(() {ascensore = "Tutto";}); },
                    style: ascensore == "Tutto"? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                    child: const Text('Tutto'),
                  ),
                  ElevatedButton(
                onPressed: ascensore == "Si"? null: () { setState(() {ascensore = "Si";}); },
                style: ascensore == "Si"? AccessController.not_clickable_style_button : AccessController.clickable_style_button,
                child: const Text('Si'),
              ),
    
              ElevatedButton(
                onPressed: ascensore == "No"? null:() { setState(() {ascensore = "No";}); },
                style: ascensore == "No"? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                child: const Text('No'),
              ),
                ],
              )
              
          

            ],
          ),
          Divider(
            color: Colors.grey,
            thickness: 1,
            height: 1, // spazio sopra e sotto
          ),
          Column(
            children: [
              Text("Stato Immobile",style: TextStyle(fontSize: 24),), 
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: stato == "Tutto"? null:() { setState(() {stato = "Tutto";}); },
                    style: stato == "Tutto"? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                    child: const Text('Tutto'),
                  ),
                  ElevatedButton(
                    onPressed: stato == "Nuovo"? null:() { setState(() {stato = "Nuovo";}); },
                    style: stato == "Nuovo"? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                    child: const Text('Nuovo'),
                  ),
                  ElevatedButton(
                    onPressed: stato == "Ottimo"? null:() { setState(() {stato = "Ottimo";}); },
                    style: stato == "Ottimo"? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                    child: const Text('Ottimo'),
                  ),
                  ElevatedButton(
                    onPressed: stato == "Buono"? null:() { setState(() {stato = "Buono";}); },
                    style: stato == "Buono"? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                    child: const Text('Buono'),
                  ),
                  ElevatedButton(
                    onPressed: stato == "Da ristrutturare"? null:() { setState(() {stato = "Da ristrutturare";}); },
                    style: stato == "Da ristrutturare"? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                    child: const Text('Da ristrutturare'),
                  ),
                ],
              )
            ],
          ),
          Divider(
            color: Colors.grey,
            thickness: 1,
            height: 1, // spazio sopra e sotto
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            spacing: 5,
            children: [
              Text("Garage",style: TextStyle(fontSize: 24),),  
              NumberPicker(
                    itemCount: 3,
                    itemWidth: 200,
                    itemHeight: 30,
                    value: garage,
                    minValue: 0,
                    maxValue: 1 << 10,
                    step: 1,
                    onChanged: (value) => setState(() => garage = value),
               ),  
              
              
            ],
          ),
          Divider(
            color: Colors.grey,
            thickness: 1,
            height: 1, // spazio sopra e sotto
          ),
          Column(
            children: [
              Text("Classe Energetica",style: TextStyle(fontSize: 24),),
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
              )
              
            ],
          ),

          ElevatedButton(
                    onPressed: () {
                    my_search_controller.SearchController.filteredSearch(
                      context,
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
                      widget.citta
                    );
                  },
                  style: ButtonStyle(
                    padding: WidgetStateProperty.all(EdgeInsets.symmetric(vertical: 20, horizontal: 10)),
                    backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                    shape: WidgetStateProperty.all(
                      RoundedRectangleBorder(borderRadius: BorderRadius.circular(32)),
                    ),
                  ),
                  child: Row(mainAxisSize: MainAxisSize.min, children: [
                    Icon(
                      Icons.location_on_rounded,
                      color: Colors.white,
                      size: 50,
                    ),
                    Text(
                      "Cerca",
                      style: TextStyle(fontSize: 25),
                    )
                  ]),
                ),
          SizedBox(width: 10,)

        ],
      ),
      ) 
      
        
    );
  }
}


