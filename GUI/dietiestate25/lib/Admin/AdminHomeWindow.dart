import 'package:dietiestate25/Admin/NotificationAdminWindow.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';


class AdminHomeWindow extends StatefulWidget {
  const AdminHomeWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<AdminHomeWindow> createState() => _AdminHomeWindowState();
}

class _AdminHomeWindowState extends State<AdminHomeWindow> {
  int _selectedIndex = 0;

  final List<Widget> _pages = [
    //BookingWindow(),
    HomeScreen(),
    NotificationAdminWindow(),
    //AdminAppointmentWindow(),
    ProfileAdminWindow(),
    //ProfileWindow(),
    //ProfileAdminWindow(),
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

class ProfileAdminWindow extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Text("Profile Admin Window"),
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
  

  @override
  void initState() {
    super.initState();

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Text("Ciao Admin sei nella home"),
    );
  }
}
