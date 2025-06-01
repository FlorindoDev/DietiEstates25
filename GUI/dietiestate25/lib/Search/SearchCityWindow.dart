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
                  print("City: $city");
                },
                decoration: InputDecoration(
                  labelText: 'Cittá',
                  border: OutlineInputBorder(),
                  suffixIcon: IconButton(
                    icon: Icon(Icons.search),
                    onPressed: () async {
                      final res = await my_search_controller.SearchController.searchCity(context, city);
                      print(res.length);
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
                      });
                    },
                  ),
                ),
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
