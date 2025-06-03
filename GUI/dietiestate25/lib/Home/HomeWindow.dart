import 'package:dietiestate25/Home/HomeController.dart';
import 'package:dietiestate25/ManagementAccount/ProfileWindow.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/RouteWindows/RouteWindows.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

import 'package:dietiestate25/NotificationClass/NotificationWindow.dart';
import 'package:dietiestate25/CalendarClass/CalendarWindow.dart';

class HomeWindow extends StatefulWidget {
  const HomeWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<HomeWindow> createState() => _HomeWindowState();
}

class _HomeWindowState extends State<HomeWindow> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    HomeScreen(),
    NotificationWindow(),
    CalendarWindow(),
    ProfileWindow(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: _pages[_selectedIndex], // il contenuto del body e determinato dalla classe correnete a schermo nella lista
      bottomNavigationBar: NavigationBar(
        selectedIndex: _selectedIndex,
        onDestinationSelected: (index) => setState(() => _selectedIndex = index),
        destinations: const [
          NavigationDestination(
            icon: Icon(Icons.search, color: Colors.white),
            label: 'Home',
          ),
          NavigationDestination(
            icon: Icon(Icons.notifications, color: Colors.white),
            label: 'Notifiche',
          ),
          NavigationDestination(
            icon: Icon(Icons.calendar_month, color: Colors.white),
            label: 'Appuntamenti',
          ),
          NavigationDestination(
            icon: Icon(Icons.person, color: Colors.white),
            label: 'Profilo',
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


class HomeScreen extends StatefulWidget {

  const HomeScreen({super.key});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {

  late Future<List<Estate>> estates;


  bool isScrolled = false;

  void reload() {
    setState(() {
      estates = HomeController.getEstatesHome(context, "");
      
    });
  }

  @override
  void initState() {
    super.initState();
    
    estates = HomeController.getEstatesHome(context,"");
  
  }

   @override
  void dispose() {
    super.dispose();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold( body:  Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              spacing: 10,
              children: [
                ElevatedButton(
                  onPressed: () {
                    Navigator.of(context)
                            .pushNamed(RouteWindows.searchHomeWindow);
                  },
                  style: ButtonStyle(
                    padding: WidgetStateProperty.all(EdgeInsets.symmetric(vertical: 20, horizontal: 10)),
                    backgroundColor: WidgetStateProperty.all(MyApp.rosso),
                    foregroundColor: WidgetStateProperty.all(Colors.white),
                    shape: WidgetStateProperty.all(
                      RoundedRectangleBorder(borderRadius: BorderRadius.circular(32)),
                    ),
                  ),
                  child: Row(mainAxisSize: MainAxisSize.min, children: [
                    Icon(
                      Icons.location_on_rounded,
                      color: Colors.white,
                      size: 50,
                    ),
                    Text(
                      "Ricerca Immobile",
                      style: TextStyle(fontSize: 25),
                    )
                  ]),
                ),
                Expanded(
                  child: Container(
                      //width: double.infinity,
                      decoration: BoxDecoration(
                        color: MyApp.celesteSfumato,
                        borderRadius: BorderRadius.only(
                          topLeft: Radius.circular(30),
                          topRight: Radius.circular(30),
                          bottomLeft: Radius.circular(0),
                          bottomRight: Radius.circular(0),
                        ),
                      ),
                  
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Padding(padding: EdgeInsets.only(left: 20,top: 10), child:  Text('Nuovi Annunci', style: TextStyle(fontSize: 32 ), textAlign: TextAlign.left, )),
                          FutureBuilder<List<Estate>>(
                            future: estates,
                            builder: (context, snapshot) {
                              if (snapshot.connectionState == ConnectionState.waiting) {
                                return const Center(
                                        child: CircularProgressIndicator(strokeWidth: 5,
                                          color: Colors.white,
                                        ),
                                      );
                              } else if (snapshot.hasError) {
                                WidgetsBinding.instance.addPostFrameCallback((_) {
                                        MyApp.mostraPopUpWarining(
                                            context, 'Error', "Errore riprova più tardi");
                                      });
                                return const Center();
                              } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                                WidgetsBinding.instance.addPostFrameCallback((_) {
                                        MyApp.mostraPopUpWarining(
                                            context, 'Error', "Nessun estates trovato");
                                      });
                                return const Center();
                              } 
                                List<Estate> estates = snapshot.data!;
                                return Flexible(
                                  
                                  child: ListView.builder(
                                    scrollDirection: Axis.vertical,
                                    itemCount: estates.length,
                                    itemBuilder: (context, index) {
                                      Estate estate = estates[index];
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
                                  ),
                                );

                        },
                      ),
                    ],
                  ),
                      
              ),
            )
          ],
        
    ),
    );
  }
}
