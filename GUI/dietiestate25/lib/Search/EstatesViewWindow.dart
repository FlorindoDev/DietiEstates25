import 'dart:ffi';

import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Search/SearchController.dart' as my_search_controller;
import 'package:numberpicker/numberpicker.dart';

class EstatesViewWindow extends StatefulWidget {
  const EstatesViewWindow({super.key, required this.appbar, required this.estates});
  final AppBar appbar;
  final List<Estate> estates;

  @override
  State<EstatesViewWindow> createState() => _EstatesViewWindowState();
}

class _EstatesViewWindowState extends State<EstatesViewWindow> {
 
  @override
  Widget build(BuildContext context) {
    
    

    return Scaffold(
      appBar: widget.appbar,
      body: ListView.builder(
        scrollDirection: Axis.vertical,
        itemCount: widget.estates.length,
        itemBuilder: (context, index) {
          Estate estate = widget.estates[index];
          return SizedBox( // Imposta una larghezza fissa o vincolata
            width: 200,
            child: 
            InkWell(
              onTap: (){
                print(estate);
              },
              child : Card(
              elevation: 10,
              clipBehavior: Clip.hardEdge,
              margin: const EdgeInsets.symmetric(vertical: 10, horizontal: 20),
              child: Padding(
                    padding: const EdgeInsets.only(left: 10,bottom: 10),
                    child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Icon(Icons.home, size: 100,),
                   Text(
                      '€ ${estate.price}',
                      style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 18),
                    ),
                  
                  
                    
                    Text(
                      '${estate.mode} - ${estate.indirizzo?.citta} ${estate.indirizzo?.quartiere} - ${estate.indirizzo?.via} ${estate.indirizzo?.numeroCivico}',
                      style: const TextStyle(fontWeight: FontWeight.bold),
                    ),
                  
                  
                    
                    Row(
                    spacing: 50,
                    children: [
                      Row(children: [
                        Icon(Icons.border_all_outlined, size: 40,),
                        Text('${estate.floor} m2')
                      ],
                      
                      ),
                      Row(children: [
                        Icon(Icons.meeting_room_rounded, size: 40,),
                        Text('${estate.rooms}')
                      ],
                      
                      ),
                      Row(children: [
                        Icon(Icons.bathtub, size: 40,),
                        Text('${estate.wc}')
                      ],
                      
                      ),
                      Row(children: [
                        Icon(Icons.garage, size: 40,),
                        Text('${estate.garage}')
                      ],
                      
                      ),
                    ],
                  ),
                ],
                ),
              ),
            ),
            ),
              
          );
        },
      ), 
      
        
    );
  }
}


