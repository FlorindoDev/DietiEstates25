import 'dart:collection';
import 'dart:convert';
import 'package:dietiestate25/AccessClass/AccessController.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Search/SearchController.dart'
    as my_search_controller;

class EstatesViewWindow extends StatefulWidget {
  EstatesViewWindow({super.key, required this.appbar, required this.estates});
  final AppBar appbar;
  final List<Estate> estates;

  final HashMap<Estate, int> indexFoto = HashMap();
  final HashMap<Estate, String> fotoLoded = HashMap();

  @override
  State<EstatesViewWindow> createState() => _EstatesViewWindowState();
}

class _EstatesViewWindowState extends State<EstatesViewWindow> {
  int page = 1;

  void prevFoto(Estate estate) {
    setState(() {
      widget.indexFoto[estate] = widget.indexFoto[estate]! - 1;
      widget.fotoLoded[estate] = estate.foto![widget.indexFoto[estate] ?? 0];
    });
  }

  void nextFoto(Estate estate) {
    setState(() {
      widget.indexFoto[estate] =
          widget.indexFoto[estate] != null ? widget.indexFoto[estate]! + 1 : 1;
      widget.fotoLoded[estate] = estate.foto![widget.indexFoto[estate] ?? 0];
    });
  }

  void initState() {
    super.initState();
    // Inizializza fotoLoded e indexFoto per ogni estate
    for (var estate in widget.estates) {
      if (estate.foto != null && estate.foto!.isNotEmpty) {
        widget.indexFoto[estate] = 0;
        widget.fotoLoded[estate] = estate.foto![0];
      } else {
        widget.indexFoto[estate] = 0;
        widget.fotoLoded[estate] = ""; // Nessuna foto
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: SingleChildScrollView(
        child: ListView.builder(
          scrollDirection: Axis.vertical,
          shrinkWrap: true,
          physics: NeverScrollableScrollPhysics(),
          itemCount: widget.estates.length,
          itemBuilder: (context, index) {
            Estate estate = widget.estates[index];
            //print("\n\nPrima casa : ${estate}\n");
            return SizedBox(
              // Imposta una larghezza fissa o vincolata
              width: 200,
              child: Card(
                elevation: 10,
                clipBehavior: Clip.hardEdge,
                margin:
                    const EdgeInsets.symmetric(vertical: 10, horizontal: 20),
                child: Padding(
                  padding: const EdgeInsets.only(left: 10, bottom: 10),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(top: 10),
                        child: Center(
                          child: estate.foto != null && estate.foto!.isNotEmpty
                              ? SingleChildScrollView(
                                  scrollDirection: Axis.horizontal,
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    crossAxisAlignment:
                                        CrossAxisAlignment.center,
                                    children: [
                                      IconButton(
                                        onPressed:
                                            (widget.indexFoto[estate] ?? 0) > 0
                                                ? () {
                                                    prevFoto(estate);
                                                  }
                                                : null,
                                        icon: const Icon(
                                            Icons.arrow_circle_left_rounded,
                                            size: 30),
                                      ),
                                      InkWell(
                                        splashColor: Colors.transparent,
                                        highlightColor: Colors.transparent,
                                        hoverColor: Colors.transparent,
                                        onTap: () {
                                          RouteWindows.selectedEstate = estate;
                                          Navigator.of(context).pushNamed(
                                              RouteWindows.estateInfoWindow);
                                        },
                                        child: Image.memory(
                                          base64Decode(
                                              widget.fotoLoded[estate] ?? ""),
                                          height: 250,
                                          width: 250,
                                          fit: BoxFit.cover,
                                        ),
                                      ),
                                      IconButton(
                                        onPressed:
                                            ((widget.indexFoto[estate] ?? 0) +
                                                        1) <
                                                    (estate.foto?.length ?? 0)
                                                ? () {
                                                    nextFoto(estate);
                                                  }
                                                : null,
                                        icon: const Icon(
                                            Icons.arrow_circle_right_rounded,
                                            size: 30),
                                      ),
                                    ],
                                  ),
                                )
                              : InkWell(
                                  splashColor: Colors.transparent,
                                  highlightColor: Colors.transparent,
                                  hoverColor: Colors.transparent,
                                  onTap: () {
                                    RouteWindows.selectedEstate = estate;
                                    Navigator.of(context).pushNamed(
                                        RouteWindows.estateInfoWindow);
                                  },
                                  child: const Icon(Icons.home, size: 250),
                                ),
                        ),
                      ),
                      InkWell(
                        splashColor: Colors.transparent,
                        highlightColor: Colors.transparent,
                        hoverColor: Colors.transparent,
                        onTap: () {
                          RouteWindows.selectedEstate = estate;
                          Navigator.of(context)
                              .pushNamed(RouteWindows.estateInfoWindow);
                        },
                        child: Padding(
                          padding: const EdgeInsets.only(top: 10, right: 10),
                          child: Column(
                            mainAxisSize: MainAxisSize.min,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                '€ ${estate.price}',
                                style: const TextStyle(
                                    fontWeight: FontWeight.bold, fontSize: 18),
                              ),
                              SizedBox(height: 5),
                              Text(
                                '${estate.mode} - ${estate.indirizzo?.citta} ${estate.indirizzo?.quartiere} - ${estate.indirizzo?.via} ${estate.indirizzo?.numeroCivico}',
                                style: const TextStyle(
                                    fontWeight: FontWeight.bold),
                                overflow: TextOverflow.ellipsis,
                              ),
                              const SizedBox(height: 10),
                              SingleChildScrollView(
                                scrollDirection: Axis.horizontal,
                                child: Row(
                                  children: [
                                    Row(
                                      children: [
                                        const Icon(Icons.border_all_outlined,
                                            size: 30),
                                        const SizedBox(width: 5),
                                        Text('${estate.space} m2'),
                                      ],
                                    ),
                                    const SizedBox(width: 20),
                                    Row(
                                      children: [
                                        const Icon(Icons.meeting_room_rounded,
                                            size: 30),
                                        const SizedBox(width: 5),
                                        Text('${estate.rooms}'),
                                      ],
                                    ),
                                    const SizedBox(width: 20),
                                    Row(
                                      children: [
                                        const Icon(Icons.bathtub, size: 30),
                                        const SizedBox(width: 5),
                                        Text('${estate.wc}'),
                                      ],
                                    ),
                                    const SizedBox(width: 20),
                                    Row(
                                      children: [
                                        const Icon(Icons.garage, size: 30),
                                        const SizedBox(width: 5),
                                        Text('${estate.garage}'),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            );
          },
        ),
      ),
      bottomSheet: Row(
        spacing: 5,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          ElevatedButton(
            onPressed: my_search_controller.SearchController.page == 1
                ? null
                : () {
                    setState(() {
                      my_search_controller.SearchController.filteredSearch(
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
                          my_search_controller.SearchController.citta,
                          my_search_controller.SearchController.latitudine,
                          my_search_controller.SearchController.longitudine,
                          my_search_controller.SearchController.radius);
                    });
                  },
            style: my_search_controller.SearchController.page == 1
                ? AccessController.not_clickable_style_button
                : AccessController.clickable_style_button,
            child: const Text('Indietro'),
          ),
          ElevatedButton(
            onPressed: () {
              setState(() {
                my_search_controller.SearchController.filteredSearch(
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
                    my_search_controller.SearchController.citta,
                    my_search_controller.SearchController.latitudine,
                    my_search_controller.SearchController.longitudine,
                    my_search_controller.SearchController.radius);
              });
            },
            style: AccessController.clickable_style_button,
            child: const Text('Avanti'),
          ),
        ],
      ),
    );
  }
}
