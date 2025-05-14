import 'package:dietiestate25/Admin/AdminHomeController.dart';
import 'package:dietiestate25/Admin/ManageAdminWindow.dart';
import 'package:dietiestate25/Admin/NotificationAdminWindow.dart';
import 'package:dietiestate25/main.dart';
import 'package:lucide_icons/lucide_icons.dart';
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
      
      body: Container(
        padding: const EdgeInsets.only(top: 20),
        width: double.infinity,
        decoration: BoxDecoration(
          color: MyApp.celeste,
          borderRadius: BorderRadius.only(
            topLeft: Radius.circular(30),
            topRight: Radius.circular(30),
            bottomLeft: Radius.circular(0),
            bottomRight: Radius.circular(0),
          ),
        ),
        child: Column(
        
        children: [
          Container(
          width: 800,
          margin: const EdgeInsets.symmetric(horizontal: 20),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(20),
            boxShadow: [
              BoxShadow(
                color: Colors.black.withOpacity(0.1),
                blurRadius: 10,
                offset: const Offset(0, 5),
              ),
            ],
          ),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              _buildSection(
                icon: LucideIcons.users,
                title: 'Agenti',
                buttons: [
                  _buildButton(
                    'Crea Agente', 
                    () {
                      
                    }
                  ),
                  
                  _buildButton(
                    'Gestisci Agenti', 
                    () {
                      
                    }
                  ),
                  
                ],
              ),
              const SizedBox(height: 20),
              AdminHomeController.isSupporto() ? const Center() : _buildSection(
                icon: LucideIcons.userCog2,
                title: 'Amministratori',
                buttons: [
                  _buildButton('Crea Amministratore', () {}),
                  _buildButton('Gestisci Amministratori', () {

                      
                      Navigator.of(context).push(
                        MaterialPageRoute(
                          builder: (context) => new ManageAdminWindow(
                            appbar: MyApp.smallAppBar,
            
                          ),
                        ),
                      );

                  }),
                ],
              ),
            ],
          ),
        ),
        ],
      ),

      ), 
      
        
    );
  }

  Widget _buildSection({required IconData icon, required String title, required List<Widget> buttons}) {
    return Card(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
      elevation: 3,
      child: Padding(
        padding: const EdgeInsets.all(15),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(icon, size: 24, color: MyApp.blu),
                const SizedBox(width: 10),
                Text(
                  title,
                  style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ],
            ),
            const Divider(height: 20, thickness: 1),
            Column(children: buttons),
          ],
        ),
      ),
    );
  }

  Widget _buildButton(String label, VoidCallback onPressed) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 5),
      child: ElevatedButton(
        onPressed: onPressed,
        style: ElevatedButton.styleFrom(
          backgroundColor: MyApp.blu,
          foregroundColor: Colors.white,
          minimumSize: const Size(double.infinity, 40),
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
        ),
        child: Text(label),
      ),
    );
  }
}
