import 'package:dietiestate25/AgentHome/AgentHomeController.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/AgentHome/NotificationAgentWindow.dart';
import 'package:dietiestate25/AgentHome/AgentAppointmentWindow.dart';
import 'package:dietiestate25/ManagementAccount/ProfileWindow.dart';
import 'package:dietiestate25/Booking/BookingWindow.dart';

class AgentHomeWindow extends StatefulWidget {
  const AgentHomeWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<AgentHomeWindow> createState() => _AgentHomeWindowState();
}

class _AgentHomeWindowState extends State<AgentHomeWindow> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    ProfileWindow(),
    //HomeScreen(),
    NotificationAgentWindow(),
    AgentAppointmentWindow(),
    //ProfileAgentWindow(),
    //ProfileWindow(),
    BookingWindow(),
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

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreen();
}

// Pagine separate che verranno visualizzate
class _HomeScreen extends State<HomeScreen> {
  late Future<List<Estate>> estates;
  // Variabile per tenere traccia del filtro selezionato.
  bool _errorShown = false;
  bool _noDataShown = false;

  @override
  void initState() {
    super.initState();
    estates = AgentHomeController.getEstate(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Expanded(
        child: FutureBuilder<List<Estate>>(
          future: estates,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(
                child: CircularProgressIndicator(strokeWidth: 5),
              );
            }
            if (snapshot.hasError && !_errorShown) {
              //MyApp.mostraPopUpInformativo(context, 'Error', "Errore riprova più tardi");
              WidgetsBinding.instance.addPostFrameCallback((_) {
                MyApp.mostraPopUpWarining(
                    context, 'Error', "Errore riprova più tardi");
              });
            }
            if (!snapshot.hasData || snapshot.data!.isEmpty && !_noDataShown) {
              WidgetsBinding.instance.addPostFrameCallback((_) {
                MyApp.mostraPopUpWarining(
                    context, 'Error', "Errore riprova più tardi");
              });
            }
            return ListView.builder(
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                return Padding(
                  padding: const EdgeInsets.all(8),
                  child: Card(
                    elevation: 10.0,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(25),
                      side: BorderSide(color: MyApp.blu, width: 2),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.all(12.0),
                      child: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          //qui vanno gli estate
                        ],
                      ),
                    ),
                  ),
                );
              },
            );
          },
        ),
      ),
    );
  }
}
