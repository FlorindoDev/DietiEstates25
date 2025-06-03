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
  int page = 1;

  

 
  @override
  Widget build(BuildContext context) {
    
    

    return Scaffold(
      appBar: widget.appbar,
      body: SingleChildScrollView(
        child: 
      
        ListView.builder(
        scrollDirection: Axis.vertical,
         shrinkWrap: true,
        physics: NeverScrollableScrollPhysics(), 
        itemCount: widget.estates.length,
        itemBuilder: (context, index) {
          Estate estate = widget.estates[index];
          return SizedBox( // Imposta una larghezza fissa o vincolata
            width: 200,
            child: 
            InkWell(
              splashColor: Colors.transparent,
              highlightColor: Colors.transparent,
              hoverColor: Colors.transparent,
              onTap: (){
                RouteWindows.selectedEstate = estate;
                Navigator.of(context).pushNamed(RouteWindows.estateInfoWindow);
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
      ),),
      bottomSheet: 
          Row(
            spacing: 5,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ElevatedButton(
                    onPressed: my_search_controller.SearchController.page == 1? null:() { setState(() { my_search_controller.SearchController.filteredSearch(
                      context,
                      my_search_controller.SearchController.page - 1,
                      my_search_controller.SearchController.mode,
                      my_search_controller.SearchController.minPrice,
                      my_search_controller.SearchController.maxPrice,
                      my_search_controller.SearchController.minDimensione,
                      my_search_controller.SearchController.maxDimensione,
                      my_search_controller.SearchController.minStanze,
                      my_search_controller.SearchController.maxStanze,
                      my_search_controller.SearchController.bagni,
                      my_search_controller.SearchController.ascensore,
                      my_search_controller.SearchController.stato,
                      my_search_controller.SearchController.garage,
                      my_search_controller.SearchController.opzioni,
                      my_search_controller.SearchController.citta
                    );}); },
                    style: my_search_controller.SearchController.page == 1? AccessController.not_clickable_style_button :AccessController.clickable_style_button,
                    child: const Text('Indietro'),
                  ),
              ElevatedButton(
                    onPressed: () { setState(() { my_search_controller.SearchController.filteredSearch(
                      context,
                      my_search_controller.SearchController.page + 1,
                      my_search_controller.SearchController.mode,
                      my_search_controller.SearchController.minPrice,
                      my_search_controller.SearchController.maxPrice,
                      my_search_controller.SearchController.minDimensione,
                      my_search_controller.SearchController.maxDimensione,
                      my_search_controller.SearchController.minStanze,
                      my_search_controller.SearchController.maxStanze,
                      my_search_controller.SearchController.bagni,
                      my_search_controller.SearchController.ascensore,
                      my_search_controller.SearchController.stato,
                      my_search_controller.SearchController.garage,
                      my_search_controller.SearchController.opzioni,
                      my_search_controller.SearchController.citta
                    );
                    
                    }); },
                    style:AccessController.clickable_style_button,
                    child: const Text('Avanti'),
                  ),
            ],
        
      ),
        
    );
  }
}


