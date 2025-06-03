import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

class SearchHomeWindow extends StatefulWidget {
  const SearchHomeWindow({super.key, required this.appbar});
  final AppBar appbar;

  @override
  State<SearchHomeWindow> createState() => _SearchHomeWindowState();
}

class _SearchHomeWindowState extends State<SearchHomeWindow> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: Column(
        children: [
          Container(
            width: double.infinity,
            decoration: BoxDecoration(
              color: Colors.brown,
              borderRadius: BorderRadius.only(
                bottomLeft: Radius.circular(20),
                bottomRight: Radius.circular(20),
              ),
            ),
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: ElevatedButton(
            
              style: ButtonStyle(
                backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                
              ),
              onPressed: (){
                  Navigator.of(context).pushNamed(RouteWindows.searchCityWindow);

                
              }, 
              child: Text(
                "Cerca per città", 
                style: TextStyle(
                  color: Colors.white,
                ),
                ),
              ),
            ), 
            
            
          ),
          // Add more widgets here as needed
        ],
      ),
    );
  }


}