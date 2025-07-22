import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';

class EstateInfoWindowMapView extends StatefulWidget {
  const EstateInfoWindowMapView({super.key, required this.latitude, required this.longitude});
  final double latitude;
  final double longitude;

  @override
  State<EstateInfoWindowMapView> createState() => _EstateInfoWindowMapViewState();

}

class _EstateInfoWindowMapViewState extends State<EstateInfoWindowMapView> {

  final MapController _mapController = MapController();
  late LatLng _currentPosition;

  @override
  void initState() {
    super.initState();
    _currentPosition = LatLng(widget.latitude, widget.longitude);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Posizione Immobile', style: TextStyle(),),
        backgroundColor: MyApp.celesteSfumato,
      ),
      body: Column(
        children:[
          Expanded(
                child: 
                    FlutterMap(
                        mapController: _mapController,
                        options: MapOptions(
                          initialZoom: 11, 
                          initialCenter: _currentPosition,
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
                          MarkerLayer( // marker
                            markers: [
                              Marker(
                                point: _currentPosition,
                                width: 40,
                                height: 40,
                                child: Icon(
                                  Icons.home,
                                  size: 40,
                                  color: MyApp.blu,
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
              ),
          ]
      ),
    );
  }
}