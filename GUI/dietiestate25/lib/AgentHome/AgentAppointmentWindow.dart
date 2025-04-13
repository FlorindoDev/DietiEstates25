import 'package:flutter/material.dart';
import 'package:dietiestate25/AgentHome/AgentHomeController.dart';
import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentNotification.dart';
import 'package:dietiestate25/main.dart';

/// Schermata principale con la lista degli appuntamenti
class AgentAppointmentWindow extends StatefulWidget {
  @override
  State<AgentAppointmentWindow> createState() => _AgentAppointmentWindowState();
}

class _AgentAppointmentWindowState extends State<AgentAppointmentWindow> {
  late Future<List<Appointment>> appointments;
  // Variabile per tenere traccia del filtro selezionato.
  String selectedFilter = "Tutte";

  // Variabile per controllare se la lista è stata scrollata.
  bool isScrolled = false;
  late ScrollController _scrollController;
  bool _errorShown = false;
  bool _noDataShown = false;

  @override
  void initState() {
    super.initState();
    appointments = AgentHomeController.getAppointment(context, "");
    _scrollController = ScrollController();
    _scrollController.addListener(() {
      if (_scrollController.offset > 0 && !isScrolled) {
        setState(() {
          isScrolled = true;
        });
      } else if (_scrollController.offset <= 0 && isScrolled) {
        setState(() {
          isScrolled = false;
        });
      }
    });
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  // Funzione di utilità per decidere il colore del bottone in base al filtro e allo scroll.
  Color _getButtonColor(String filter) {
    if (selectedFilter == filter) {
      return isScrolled ? MyApp.celeste : MyApp.celeste;
    } else {
      return MyApp.blu;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Container(
            color: isScrolled
                ? const Color.fromARGB(255, 235, 243, 244)
                : const Color.fromARGB(255, 255, 255, 255),
            padding: const EdgeInsets.symmetric(vertical: 8),
            child: Center(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        selectedFilter = "Da confermare";
                        appointments = AgentHomeController.getAppointment(
                            context, "&esiti='Da decidere'");
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _getButtonColor("Da confermare"),
                      shape: const StadiumBorder(),
                      padding: const EdgeInsets.symmetric(
                          horizontal: 16, vertical: 8),
                    ),
                    child: const Text(
                      'Da confermare',
                      style: TextStyle(color: Colors.black),
                    ),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        selectedFilter = "Accettate";
                        appointments = AgentHomeController.getAppointment(
                            context, "&esiti='Accettato'");
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _getButtonColor("Accettate"),
                      shape: const StadiumBorder(),
                      padding: const EdgeInsets.symmetric(
                          horizontal: 16, vertical: 8),
                    ),
                    child: const Text(
                      'Accettate',
                      style: TextStyle(color: Colors.black),
                    ),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        selectedFilter = "Rifiutate";
                        appointments = AgentHomeController.getAppointment(
                            context, "&esiti='Rifiutato'");
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _getButtonColor("Rifiutate"),
                      shape: const StadiumBorder(),
                      padding: const EdgeInsets.symmetric(
                          horizontal: 16, vertical: 8),
                    ),
                    child: const Text(
                      'Rifiutate',
                      style: TextStyle(color: Colors.black),
                    ),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        selectedFilter = "Tutte";
                        appointments =
                            AgentHomeController.getAppointment(context, "");
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _getButtonColor("Tutte"),
                      shape: const StadiumBorder(),
                      padding: const EdgeInsets.symmetric(
                          horizontal: 16, vertical: 8),
                    ),
                    child: const Text(
                      'Tutte',
                      style: TextStyle(color: Colors.black),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: FutureBuilder<List<Appointment>>(
              future: appointments,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(
                    child: CircularProgressIndicator(strokeWidth: 5),
                  );
                }
                if (snapshot.hasError && !_errorShown) {
                  WidgetsBinding.instance.addPostFrameCallback((_) {
                    MyApp.mostraPopUpInformativo(
                        context, 'Error', "Errore riprova più tardi");
                  });
                }
                if ((!snapshot.hasData || snapshot.data!.isEmpty) &&
                    !_noDataShown) {
                  WidgetsBinding.instance.addPostFrameCallback((_) {
                    MyApp.mostraPopUpInformativo(
                        context, 'Error', "Errore riprova più tardi");
                  });
                }
                return ListView.builder(
                  controller: _scrollController,
                  itemCount: snapshot.data!.length,
                  itemBuilder: (context, index) {
                    final appointment = snapshot.data![index];
                    return InkWell(
                      onTap: () {
                        // Navigazione verso la schermata di dettaglio dell'appuntamento
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => AppointmentDetailScreen(
                                appointment: appointment),
                          ),
                        );
                      },
                      child: Padding(
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
                                Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Expanded(
                                      child: Text(
                                        appointment.esito,
                                        style: const TextStyle(
                                          fontSize: 16,
                                          fontWeight: FontWeight.bold,
                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 32,
                                      height: 32,
                                      decoration: BoxDecoration(
                                        shape: BoxShape.circle,
                                        border: Border.all(
                                          color: const Color.fromARGB(
                                              255, 0, 0, 0),
                                          width: 3,
                                        ),
                                      ),
                                      child: appointment.icon(),
                                    ),
                                  ],
                                ),
                                const SizedBox(height: 8),
                                Row(
                                  children: [
                                    Text(
                                      'Data appuntamento: ' + appointment.data,
                                      style: const TextStyle(fontSize: 14),
                                    ),
                                  ],
                                ),
                                const SizedBox(height: 8),
                                Row(
                                  mainAxisAlignment: MainAxisAlignment.end,
                                  children: [
                                    Text(
                                      appointment.dataRichesta,
                                      style: const TextStyle(
                                        fontSize: 14,
                                        color: Colors.grey,
                                      ),
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
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

class AppointmentDetailScreen extends StatefulWidget {
  final Appointment appointment;

  const AppointmentDetailScreen({Key? key, required this.appointment})
      : super(key: key);

  @override
  State<AppointmentDetailScreen> createState() =>
      _AppointmentDetailScreenState();
}

class _AppointmentDetailScreenState extends State<AppointmentDetailScreen> {
  late Future<AppointmentNotification> appointmentsDetails;

  @override
  void initState() {
    super.initState();
    //appointmentsDetails = new AppointmentNotification();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Dettagli Appuntamento'),
      ),
      body: FutureBuilder<AppointmentNotification>(
        future: appointmentsDetails,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
              child: CircularProgressIndicator(strokeWidth: 5),
            );
          } else if (snapshot.hasError) {
            return Center(
              child: Text("Errore: ${snapshot.error}"),
            );
          } else if (snapshot.hasData) {
            final details = snapshot.data!;
            return ListView.builder(
              itemCount: 1,
              itemBuilder: (context, index) {
                ;
                return Padding(
                  padding: const EdgeInsets.all(8.0),
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
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Expanded(
                                child: Text(
                                  "details.message",
                                  style: const TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ),
                              Container(
                                width: 32,
                                height: 32,
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(
                                    color: const Color.fromARGB(255, 0, 0, 0),
                                    width: 3,
                                  ),
                                ),
                                child: CircleAvatar(
                                  backgroundColor: MyApp.panna,
                                  radius: 16,
                                  child: const Icon(
                                    Icons.pending_actions,
                                    color: Color.fromARGB(255, 0, 0, 0),
                                    size: 25,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          const SizedBox(height: 8),
                          Row(
                            children: [
                              Text(
                                'Data appuntamento: ' + "detail.data",
                                style: const TextStyle(fontSize: 14),
                              ),
                            ],
                          ),
                          const SizedBox(height: 8),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.end,
                            children: [
                              Text(
                                "details.dataRicezione",
                                style: const TextStyle(
                                    fontSize: 14, color: Colors.grey),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              },
            );
          }
          return const Center(
            child: CircularProgressIndicator(strokeWidth: 5),
          );
        },
      ),
    );
  }
}
