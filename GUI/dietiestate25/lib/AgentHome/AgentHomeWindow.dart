import 'package:dietiestate25/ManagementAccount/ProfileWindow.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/AgentHome/NotificationAgentWindow.dart';
import 'package:dietiestate25/AgentHome/AgentAppointmentWindow.dart';
import 'package:dietiestate25/AgentHome/ProfileAgentWindow.dart';
import 'package:dietiestate25/NotificationClass/NotificationWindow.dart';
import 'package:dietiestate25/CalendarClass/CalendarWindow.dart';

class AgentHomeWindow extends StatefulWidget {
  const AgentHomeWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<AgentHomeWindow> createState() => _AgentHomeWindowState();
}

class _AgentHomeWindowState extends State<AgentHomeWindow> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    HomeScreen(),
    //NotificationAgentWindow(),
    //AgentAppointmentWindow(),
    //ProfileAgentWindow(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: _pages[
          _selectedIndex], // il contenuto del body e determinato dalla classe correnete a schermo nella lista
      bottomNavigationBar: NavigationBar(
        selectedIndex: _selectedIndex,
        onDestinationSelected: (index) =>
            setState(() => _selectedIndex = index),
        destinations: const [
          NavigationDestination(
            icon: Icon(Icons.house_rounded, color: Colors.white),
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
        overlayColor: WidgetStateProperty.all(
            Colors.transparent), // Rimuove l'overlay al tap
        labelTextStyle: WidgetStateProperty.resolveWith<TextStyle>(
          (Set<WidgetState> states) {
            if (states.contains(WidgetState.selected)) {
              return const TextStyle(
                  color: Colors.white, fontWeight: FontWeight.bold);
            }
            return const TextStyle(
                color: Colors.white70); // Testo non selezionato
          },
        ),
      ),
    );
  }
}

// Pagine separate che verranno visualizzate
class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Container(
            width: double.infinity,
            child: Expanded(
                child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.center,
              spacing: 10,
              children: [
                Expanded(
                  child: Container(
                      //width: double.infinity,
                      decoration: BoxDecoration(
                        color: MyApp.celeste,
                        borderRadius: BorderRadius.only(
                          topLeft: Radius.circular(30),
                          topRight: Radius.circular(30),
                          bottomLeft: Radius.circular(0),
                          bottomRight: Radius.circular(0),
                        ),
                      ),
                      child: Expanded(
                        child: Column(
                          children: [
                            Text('Home Page, oh yessssssssssssssssss baby',
                                style: TextStyle(fontSize: 24)),
                            Scrollbar(
                                thickness: 20,
                                thumbVisibility: true,
                                trackVisibility: true,
                                child: Expanded(
                                    child: SingleChildScrollView(
                                        scrollDirection: Axis
                                            .horizontal, // Scroll orizzontale
                                        child: Row(
                                          children: [
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                            Card(
                                              color: Colors.white,
                                              child: Icon(
                                                Icons.home,
                                                size: 200,
                                              ),
                                            ),
                                          ],
                                        ))))
                          ],
                        ),
                      )),
                )
              ],
            ))));
  }
}
