import 'dart:ffi';

import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Search/SearchController.dart' as my_search_controller;
import 'package:numberpicker/numberpicker.dart';

class EstateInfoWindow extends StatefulWidget {
  const EstateInfoWindow({super.key, required this.appbar, required this.estate});
  final AppBar appbar;
  final Estate estate;

  @override
  State<EstateInfoWindow> createState() => _EstateInfoWindowState();
}

class _EstateInfoWindowState extends State<EstateInfoWindow> {
  int _selectedIndex = 0;
 
  @override
  Widget build(BuildContext context) {
    
    final List<Widget> _pages = [
      InfoScreen(estate: widget.estate,),
      PrenotazioniWindow(estate: widget.estate,),
    ];

    
    return Scaffold(
      appBar: widget.appbar,
      body: _pages[_selectedIndex] ,


      bottomNavigationBar: NavigationBar(
        selectedIndex: _selectedIndex,
        onDestinationSelected: (index) => setState(() => _selectedIndex = index),
        destinations: const [
          NavigationDestination(
            icon: Icon(Icons.info, color: Colors.white),
            label: 'Info',
          ),
          NavigationDestination(
            icon: Icon(Icons.calendar_month, color: Colors.white),
            label: 'Prenotazioni',
          ),
        ],
        indicatorColor: MyApp.celeste,
        backgroundColor: MyApp.blu,
        overlayColor: WidgetStateProperty.all(Colors.transparent), // Rimuove l'overlay al tap
        labelTextStyle: WidgetStateProperty.resolveWith<TextStyle>(
          (Set<WidgetState> states) {
            if (states.contains(WidgetState.selected)) {
              return const TextStyle(color: Colors.white, fontWeight: FontWeight.bold);
            }
            return const TextStyle(color: Colors.white70); // Testo non selezionato
          },
        ),
      ),
     
        
    );
  }
}

class PrenotazioniWindow extends StatefulWidget{
  const PrenotazioniWindow({super.key, required this.estate});
  
  final Estate estate;

   @override
  _PrenotazioniWindowState createState() => _PrenotazioniWindowState();
}

class _PrenotazioniWindowState extends State<PrenotazioniWindow> {
  
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    throw UnimplementedError();
  }



}
class InfoScreen extends StatefulWidget {

  const InfoScreen({super.key, required this.estate});

  final Estate estate;

  @override
  _InfoScreenState createState() => _InfoScreenState();
}

class _InfoScreenState extends State<InfoScreen> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
      child: SizedBox(
        //height: MediaQuery.of(context).size.height,
        width: double.infinity,
        child: 
          Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            spacing: 20,
            children: [
              Icon(Icons.home, size: 100),
              Padding(
                padding: EdgeInsets.only(left: 20),
                child: Column(
                  spacing: 20,
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('Descrizione', style: TextStyle(fontSize: 24, color: MyApp.blu), ),
                        Text(widget.estate.descrizione,),
                  
                      ],
                    ),
                    Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      spacing: 5,
                      children: [
                        Text('Indirizzo', style: TextStyle(fontSize: 24, color: MyApp.blu), ),
                        Text('${widget.estate.indirizzo?.stato} - ${widget.estate.indirizzo?.citta} ${widget.estate.indirizzo?.quartiere} - ${widget.estate.indirizzo?.via} ${widget.estate.indirizzo?.numeroCivico} ${widget.estate.indirizzo?.cap}'),
                        ElevatedButton(onPressed: (){}, child: Text("Mappa")),
                      ]
                    ),

                    Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      spacing: 5,
                      children: [
                        Text('Info', style: TextStyle(fontSize: 24, color: MyApp.blu), ),
                        Row(
                          spacing: 10,
                          
                          children: [
                            Icon(Icons.attach_money_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Prezzo',),
                                Text('${widget.estate.price}',),
                              ],
                            )
                            

                          ],
                        ),
                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.border_all_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Dimensione',),
                                Text('${widget.estate.space} m2',),
                              ],
                            )
                            

                          ],
                        ),

                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.meeting_room_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Locali',),
                                Text('${widget.estate.rooms}',),
                              ],
                            )
                            

                          ],
                        ),

                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.bathroom_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Bagni',),
                                Text('${widget.estate.wc}',),
                              ],
                            )
                            

                          ],
                        ),

                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.stairs, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Paino',),
                                Text('${widget.estate.floor}',),
                              ],
                            )
                            

                          ],
                        ),

                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.elevator_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Ascensore',),
                                Text((widget.estate.elevator == true)?"Si":"No" ),
                              ],
                            )
                            

                          ],
                        ),

                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.garage_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Garage',),
                                Text('${widget.estate.garage}' ),
                              ],
                            )
                            

                          ],
                        ),

                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.energy_savings_leaf_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Classe Energetica',),
                                Text('${widget.estate.classeEnergetica}' ),
                              ],
                            )
                            

                          ],
                        ),

                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.details_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Stato',),
                                Text('${widget.estate.stato}' ),
                              ],
                            )
                            

                          ],
                        ),

                        Row(
                          spacing: 10,
                          children: [
                            Icon(Icons.content_paste_rounded, size: 40,),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text('Contratto',),
                                Text('${widget.estate.mode}' ),
                              ],
                            )
                            

                          ],
                        ),
                      ]
                    ),
                  ],
                )
              ),
             ],
          ),
        ),
      ),
    );
  }
}



