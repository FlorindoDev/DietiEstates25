import 'package:dietiestate25/Home/HomeController.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/Model/Appointment/AppointmentNotification.dart';
import 'package:dietiestate25/main.dart';

/// Schermata principale con la lista degli appuntamenti
class CalendarWindow extends StatefulWidget {
  @override
  State<CalendarWindow> createState() => _CalendarWindowState();
}

class _CalendarWindowState extends State<CalendarWindow> {
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
    appointments = HomeController.getAppointment(context, "");
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
                        appointments = HomeController.getAppointment(
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
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        selectedFilter = "Accettate";
                        appointments = HomeController.getAppointment(
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
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        selectedFilter = "Rifiutate";
                        appointments = HomeController.getAppointment(
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
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        selectedFilter = "Tutte";
                        appointments =
                            HomeController.getAppointment(context, "");
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
                      style: TextStyle(color: Colors.white),
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
                    MyApp.mostraPopUpWarining(
                        context, 'Error', "Errore riprova più tardi");
                  });
                }
                if ((!snapshot.hasData || snapshot.data!.isEmpty) &&
                    !_noDataShown) {
                  WidgetsBinding.instance.addPostFrameCallback((_) {
                    MyApp.mostraPopUpWarining(
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
  late Future<AppointmentNotification> appointmentDetails;

  @override
  void initState() {
    super.initState();
    appointmentDetails = HomeController.getAppointmentSpecific(
        context, widget.appointment.idAppointment.toString());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Dettagli Appuntamento'),
      ),
      body: FutureBuilder<AppointmentNotification>(
        future: appointmentDetails,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
              child: CircularProgressIndicator(strokeWidth: 4),
            );
          }
          if (snapshot.hasError) {
            return Center(
              child: Text('Errore: ${snapshot.error}'),
            );
          }
          if (!snapshot.hasData) {
            return const Center(
              child: Text('Nessun dettaglio disponibile'),
            );
          }

          final details = snapshot.data!;

          return SingleChildScrollView(
            padding: const EdgeInsets.all(16.0),
            child: Card(
              elevation: 8,
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(20),
              ),
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.stretch,
                  children: [
                    const Center(
                      child: CircleAvatar(
                        radius: 36,
                        backgroundColor: Colors.blueAccent,
                        child: Icon(
                          Icons.event,
                          size: 40,
                          color: Colors.white,
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),
                    Text(
                      details.nomeEcognome,
                      style: const TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      ),
                      textAlign: TextAlign.center,
                    ),
                    const SizedBox(height: 8),
                    Text(
                      details.viaEstate,
                      style: const TextStyle(fontSize: 16),
                      textAlign: TextAlign.center,
                    ),
                    const Divider(height: 32),
                    ListTile(
                      leading: const Icon(Icons.calendar_today),
                      title: const Text('Data Richiesta'),
                      subtitle: Text(details.dataRichesta),
                    ),
                    ListTile(
                      leading: const Icon(Icons.event),
                      title: const Text('Data Appuntamento'),
                      subtitle: Text(details.data),
                    ),
                    ListTile(
                      leading: const Icon(Icons.assignment_turned_in),
                      title: const Text('Esito'),
                      subtitle: Text(
                        details.esito,
                        style: TextStyle(
                          color: details.esito.toLowerCase() == 'accettato'
                              ? Colors.green
                              : Colors.red,
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ),
                    const SizedBox(height: 24),
                    ElevatedButton.icon(
                      onPressed: () {
                        Navigator.of(context).pop();
                      },
                      icon: const Icon(Icons.arrow_back, color: Colors.black),
                      label: const Text('Torna indietro',
                          style: TextStyle(color: Colors.black)),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: MyApp.rosso,
                        padding: const EdgeInsets.symmetric(
                            vertical: 12, horizontal: 24),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}
