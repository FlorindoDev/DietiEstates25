import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/Search/SearchFilterWindow.dart';
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
  LatLng _currentPosition = LatLng(40.88333000, 14.41667000); // Napoli
  double _searchRadius = 10000; // Raggio in metri
  bool _isLoadingLocation = true;
  // bool _isSearching = false;

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

      if (permission == LocationPermission.whileInUse) {
        Position position = await Geolocator.getCurrentPosition(
          desiredAccuracy: LocationAccuracy.high,
        );

        setState(() {
          _currentPosition = LatLng(position.latitude, position.longitude);
          _isLoadingLocation = false;
        });
      } else {
        setState(() {
          _isLoadingLocation = false;
        });
      }
    } catch (e) {
      setState(() {
        _isLoadingLocation = false;
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

  Future<void> _performRadiusSearch() async {
    RouteWindows.citta  = ""; // Reset city search
    SearchFilterWindow.currentPosition = _currentPosition;
    SearchFilterWindow.searchRadius = _searchRadius;
    Navigator.of(context).pushNamed(RouteWindows.searchFilterWindow);

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: Stack(
        children: [
          Column(
            children: [
              Container(
                width: double.infinity,
                decoration: BoxDecoration(
                  color: Colors.grey[200],

                ),
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: ElevatedButton(
                    
                    style: ButtonStyle(
                      backgroundColor:
                          WidgetStateProperty.all(MyApp.rosso),
                          padding: WidgetStateProperty.all(const EdgeInsets.symmetric(vertical: 14.0)),
                    ),
                    onPressed: () {
                      Navigator.of(context)
                          .pushNamed(RouteWindows.searchCityWindow);
                    },
                    child: const Text(
                      "Cerca per città",
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 18,
                        
                      ),
                    
                    ),
                  ),
                ),
              ),
              Container( 
                decoration: BoxDecoration(
                  color: Colors.grey[200],
                 
                  
                  ),
                child :
              // Controlli per il raggio di ricerca
              Container(
                padding: const EdgeInsets.all(16.0),
                margin: const EdgeInsets.only(left: 10.0, right: 10, top: 5.0, bottom: 5),
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: const BorderRadius.all(Radius.circular(15)
                  
                  ),
                ),
                
                child: Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Text(
                          'Raggio di ricerca:',
                          style: TextStyle(
                              fontSize: 18, fontWeight: FontWeight.bold),
                        ),
                        Text(
                          '${(_searchRadius / 1000).toStringAsFixed(1)} km',
                          style:
                              TextStyle(fontSize: 20, color: MyApp.blu),
                        ),
                      ],
                    ),
                    Slider(
                      value: _searchRadius,
                      min: 100,
                      max: 100000,
                      divisions: 100,
                      activeColor: MyApp.celeste,
                      onChanged: _updateSearchRadius,
                    ),
                    const SizedBox(height: 10),
                    ElevatedButton(
                      style: ButtonStyle(
                        backgroundColor:
                            WidgetStateProperty.all(MyApp.blu),
                      ),
                      // onPressed: _isSearching ? null : _performRadiusSearch,
                      onPressed: _performRadiusSearch,
                      child: const Text(
                        'Cerca nell\'area selezionata',
                        style: TextStyle(color: Colors.white, fontSize: 18),
                        
                      ),
                    ),
                  ],
                ),
              ),
              ),
              // Mappa
              Expanded(
                child: _isLoadingLocation
                    ? const Center(child: CircularProgressIndicator())
                    : FlutterMap(
                        mapController: _mapController,
                        options: MapOptions(
                          initialZoom: 10, 
                          onTap: _onMapTap,
                          initialCenter: _currentPosition,
                          // initialCenter: LatLng(14.41667000, 40.88333000),
                        ),
                        children: [
                          TileLayer(
                            urlTemplate:
                                'https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}.png',
                            subdomains: ['a', 'b', 'c', 'd'],
                            userAgentPackageName:
                                'com.example.dietiestate25',
                            maxZoom: 19,
                          ),
                          CircleLayer( // area del raggio di ricerca
                            circles: [
                              CircleMarker(
                                point: _currentPosition,
                                radius: _searchRadius,
                                useRadiusInMeter: true,
                                color: MyApp.celesteSfumato.withOpacity(0.3),
                                borderColor: MyApp.blu,
                                borderStrokeWidth: 2,
                              ),
                            ],
                          ),
                          MarkerLayer( // marker
                            markers: [
                              Marker(
                                point: _currentPosition,
                                width: 40,
                                height: 40,
                                child: Icon(
                                  Icons.location_pin,
                                  size: 40,
                                  color: MyApp.blu,
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
              ),
            ],
          ),

          // Loading overlay
          // if (_isSearching)
          //   Container(
          //     color: Colors.black.withOpacity(0.5),
          //     child: const Center(
          //       child: CircularProgressIndicator(
          //         valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
          //       ),
          //     ),
          //   ),
        ],
      ),
    );
  }
}
