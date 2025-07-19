import 'package:dietiestate25/Model/Estate/Ricerca.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Search/SearchController.dart' as my_search_controller;

class SearchCityWindow extends StatefulWidget {
  const SearchCityWindow({super.key, required this.appbar});
  final AppBar appbar;

  @override
  State<SearchCityWindow> createState() => _SearchCityWindowState();
}

class _SearchCityWindowState extends State<SearchCityWindow> {
  var city = "";
  List<dynamic> cities = [];

  List<dynamic> ricerche = [];
  
  bool _initialized = false;

  @override
  void initState() {
    super.initState();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    if (!_initialized) {
      _initialized = true;
      _loadRicerche();
    }
  }

  void _loadRicerche() async {
    var result = await my_search_controller.SearchController.getRicerche(context);
    setState(() {
      ricerche = result;
    });
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
              TextField(
                onChanged: (value) {
                  city = value;
                  
                },
                decoration: InputDecoration(
                  labelText: 'Cittá',
                  border: OutlineInputBorder(),
                  suffixIcon: IconButton(
                    icon: Icon(Icons.search),
                    onPressed: () async {
                      final res = await my_search_controller.SearchController.searchCity(context, city);
                   
                      if (res.isEmpty) {
                        ScaffoldMessenger.of(context).showSnackBar(

                          SnackBar(
                            content: Text(
                              'Nessuna città trovata',
                              style: TextStyle(color: Colors.white),
                            ),
                            animation: CurvedAnimation(
                              parent: kAlwaysDismissedAnimation,
                              curve: Curves.easeInOut,
                            ),
                            duration: Duration(seconds: 4),
                            behavior: SnackBarBehavior.floating,
                            backgroundColor: MyApp.blu,
                            action: SnackBarAction(
                              label: 'Chiudi',
                              textColor: Colors.white,
                              onPressed: () {
                                // Chiudi il SnackBar
                                ScaffoldMessenger.of(context).hideCurrentSnackBar();
                              },
                            ),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                            elevation: 10,


                          ),
                        );
                        return;
                      }
                      setState(() {
                        cities = res;
                        ricerche = List.empty();
                      });
                    },
                  ),
                ),
              ),
              SizedBox(height: 20),
              ListView.builder(
                shrinkWrap: true,
                physics: NeverScrollableScrollPhysics(),
                itemCount: ricerche.length,
                itemBuilder: (context, index) {
                  final Ricerca itemRicerca = ricerche[index];
                  
      
                  String info = "Ricerca : ";
                  String temp = itemRicerca.comando.split("?")[1];
        
                  for (var i = 0; i < itemRicerca.comando.split("?")[1].split("&").length; i++) {
                    String temp =itemRicerca.comando.split("?")[1].split("&")[i];
                   
                    if (temp.isNotEmpty && temp.split("=")[0] != "page" && temp.split("=")[0] != "limit" && temp.split("=")[0] != "sort" && temp.split("=")[0] != "desc") {
                      if(temp.split("=")[0] == "latCentroCirconferenza") info += " lat(${ double.parse(temp.split("=")[1]).toStringAsFixed(2)})";
                      else if(temp.split("=")[0] == "longCentroCirconferenza") info += " long(${double.parse(temp.split("=")[1]).toStringAsFixed(2)})";
                      else if(temp.split("=")[0] == "raggio") info += " raggio(${double.parse(temp.split("=")[1]).toStringAsFixed(2)})";
                      else info += "${temp.split("=")[1]} ";
                    }
                  }


                  return Card(
                    child: ListTile(
                      leading: Icon(Icons.watch_later),
                      title: Text(info),
                    
                      onTap: () {
                        // Azione quando si clicca su una città
                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(content: Text('Hai selezionato: $info')),                       
                        );
                        my_search_controller.SearchController.prevHistory(context, itemRicerca.comando);
                        
                      },
                    ),
                  );
                },
              ),
              
              SizedBox(height: 20),
              // Lista cities
              ListView.builder(
                shrinkWrap: true,
                physics: NeverScrollableScrollPhysics(),
                itemCount: cities.length,
                itemBuilder: (context, index) {
                  final itemCity = cities[index];
                  return Card(
                    child: ListTile(
                      leading: Icon(Icons.location_city),
                      title: Text(itemCity['name']),
                      subtitle: Text('${itemCity['admin1']}'),
                      onTap: () {
                        // Azione quando si clicca su una città
                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(content: Text('Hai selezionato: ${itemCity['name']}')),                       
                        );
                        RouteWindows.citta = itemCity['name'];
                        Navigator.of(context).pushNamed(RouteWindows.searchFilterWindow);
                      },
                    ),
                  );
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
