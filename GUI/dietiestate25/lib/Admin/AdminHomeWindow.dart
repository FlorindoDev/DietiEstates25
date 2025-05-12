import 'package:dietiestate25/AgentHome/AgentHomeController.dart';
import 'package:dietiestate25/ManagementAccount/ProfileAgentWindow.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/AgentHome/NotificationAgentWindow.dart';
import 'package:dietiestate25/AgentHome/AgentAppointmentWindow.dart';
import 'package:dietiestate25/Booking/BookingWindow.dart';

class AdminHomeWindow extends StatefulWidget {
  const AdminHomeWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  State<AdminHomeWindow> createState() => _AdminHomeWindowState();
}

class _AdminHomeWindowState extends State<AdminHomeWindow> {
  
  final List<Widget> _pages = [
    //BookingWindow(),
    //HomeScreen(),
    //NotificationAdminWindow(),
    //AdminAppointmentWindow(),
    //ProfileAdminWindow(),
    //ProfileWindow(),
    //ProfileAdminWindow(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: Text("Admin Home Window"),
     
    
      
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
