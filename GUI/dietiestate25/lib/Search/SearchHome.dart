// import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
// import 'package:dietiestate25/main.dart';
// import 'package:flutter/material.dart';

// class SearchHomeWindow extends StatefulWidget {
//   const SearchHomeWindow({super.key, required this.appbar});
//   final AppBar appbar;

//   @override
//   State<SearchHomeWindow> createState() => _SearchHomeWindowState();
// }

// class _SearchHomeWindowState extends State<SearchHomeWindow> {

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: widget.appbar,
//       body: Column(
//         children: [
//           Container(
//             width: double.infinity,
//             decoration: BoxDecoration(
//               color: Colors.brown,
//               borderRadius: BorderRadius.only(
//                 bottomLeft: Radius.circular(20),
//                 bottomRight: Radius.circular(20),
//               ),
//             ),
//             child: Padding(
//               padding: const EdgeInsets.all(16.0),
//               child: ElevatedButton(
            
//               style: ButtonStyle(
//                 backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                
//               ),
//               onPressed: (){
//                   Navigator.of(context).pushNamed(RouteWindows.searchCityWindow);

                
//               }, 
//               child: Text(
//                 "Cerca per città", 
//                 style: TextStyle(
//                   color: Colors.white,
//                 ),
//                 ),
//               ),
//             ), 
            
            
//           ),
//           // Add more widgets here as needed
//         ],
//       ),
//     );
//   }


// }

import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';
import 'package:geolocator/geolocator.dart';

class SearchHomeWindow extends StatefulWidget {
  const SearchHomeWindow({super.key, required this.appbar});
  final AppBar appbar;

  @override
  State<SearchHomeWindow> createState() => _SearchHomeWindowState();
}

class _SearchHomeWindowState extends State<SearchHomeWindow> {
  final MapController _mapController = MapController();
  LatLng _currentPosition = LatLng(41.9028, 12.4964); // Roma come default
  double _searchRadius = 1000; // Raggio in metri
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _getCurrentLocation();
  }

  Future<void> _getCurrentLocation() async {
    try {
      LocationPermission permission = await Geolocator.checkPermission();
      if (permission == LocationPermission.denied) {
        permission = await Geolocator.requestPermission();
      }

      if (permission == LocationPermission.whileInUse || 
          permission == LocationPermission.always) {
        Position position = await Geolocator.getCurrentPosition(
          desiredAccuracy: LocationAccuracy.high,
        );
        
        setState(() {
          _currentPosition = LatLng(position.latitude, position.longitude);
          _isLoading = false;
        });
      } else {
        setState(() {
          _isLoading = false;
        });
      }
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
    }
  }

  void _updateSearchRadius(double radius) {
    setState(() {
      _searchRadius = radius;
    });
  }

  void _onMapTap(TapPosition tapPosition, LatLng point) {
    setState(() {
      _currentPosition = point;
    });
  }

  void _performRadiusSearch() {
    // Implementa qui la logica di ricerca
    print('Ricerca nel raggio di $_searchRadius metri da $_currentPosition');
    
    // Esempio di navigazione con parametri
    Navigator.of(context).pushNamed(
      RouteWindows.searchCityWindow,
      arguments: {
        'latitude': _currentPosition.latitude,
        'longitude': _currentPosition.longitude,
        'radius': _searchRadius,
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: Column(
        children: [
          Container(
            width: double.infinity,
            decoration: const BoxDecoration(
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
                onPressed: () {
                  Navigator.of(context).pushNamed(RouteWindows.searchCityWindow);
                },
                child: const Text(
                  "Cerca per città",
                  style: TextStyle(
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ),
          
          // Controlli per il raggio di ricerca
          Container(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    const Text(
                      'Raggio di ricerca:',
                      style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
                    ),
                    Text(
                      '${(_searchRadius / 1000).toStringAsFixed(1)} km',
                      style: TextStyle(fontSize: 16, color: MyApp.rosso),
                    ),
                  ],
                ),
                Slider(
                  value: _searchRadius,
                  min: 100,
                  max: 50000,
                  divisions: 100,
                  activeColor: MyApp.rosso,
                  onChanged: _updateSearchRadius,
                ),
                const SizedBox(height: 10),
                ElevatedButton(
                  style: ButtonStyle(
                    backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                  ),
                  onPressed: _performRadiusSearch,
                  child: const Text(
                    'Cerca nell\'area selezionata',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
              ],
            ),
          ),
          
          // Mappa
          Expanded(
            child: _isLoading
                ? const Center(child: CircularProgressIndicator())
                : FlutterMap(
                    mapController: _mapController,
                    options: MapOptions(
                      center: _currentPosition,
                      zoom: 14,
                      onTap: _onMapTap,
                    ),
                    children: [
                      TileLayer(
                        urlTemplate: 'https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png',
                        subdomains: ['a', 'b', 'c', 'd'],
                        userAgentPackageName: 'com.example.dietiestate25',
                        maxZoom: 19,
                      ),
                      CircleLayer(
                        circles: [
                          CircleMarker(
                            point: _currentPosition,
                            radius: _searchRadius,
                            useRadiusInMeter: true,
                            color: MyApp.rosso.withOpacity(0.2),
                            borderColor: MyApp.rosso,
                            borderStrokeWidth: 2,
                          ),
                        ],
                      ),
                      MarkerLayer(
                        markers: [
                          Marker(
                            point: _currentPosition,
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
        ],
      ),
    );
  }
}