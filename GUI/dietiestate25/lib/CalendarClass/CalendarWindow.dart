import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/Home/HomeController.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';

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
            color: isScrolled ? Color.fromARGB(255, 235, 243, 244) : const Color.fromARGB(255, 255, 255, 255),
            padding: const EdgeInsets.symmetric(vertical: 8),
            child: Center(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        selectedFilter = "Da confermare";
                        appointments = HomeController.getAppointment(context, "&esiti='Da decidere'");
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _getButtonColor("Da confermare"),
                      shape: const StadiumBorder(),
                      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
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
                        appointments = HomeController.getAppointment(context, "&esiti='Accettato'");
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _getButtonColor("Accettate"),
                      shape: const StadiumBorder(),
                      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
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
                        appointments = HomeController.getAppointment(context, "&esiti='Rifiutato'");
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _getButtonColor("Rifiutate"),
                      shape: const StadiumBorder(),
                      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
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
                        appointments = HomeController.getAppointment(context, "");
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _getButtonColor("Tutte"),
                      shape: const StadiumBorder(),
                      padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
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
                  //MyApp.mostraPopUpInformativo(context, 'Error', "Errore riprova più tardi");
                  WidgetsBinding.instance.addPostFrameCallback((_) {
                    MyApp.mostraPopUpInformativo(context, 'Error', "Errore riprova più tardi");
                  });
                }
                if (!snapshot.hasData || snapshot.data!.isEmpty && !_noDataShown) {
                  WidgetsBinding.instance.addPostFrameCallback((_) {
                    MyApp.mostraPopUpInformativo(context, 'Error', "Errore riprova più tardi");
                  });
                }
                return ListView.builder(
                  controller: _scrollController,
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
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: [
                                  Expanded(
                                    child: Text(
                                      snapshot.data![index].esito,
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
                                    child: snapshot.data![index].icon(),
                                  ),
                                ],
                              ),
                              const SizedBox(height: 8),
                              Row(
                                children: [
                                  Text(
                                    'Data appuntamento: ' + snapshot.data![index].data,
                                    style: const TextStyle(fontSize: 14),
                                  ),
                                ],
                              ),
                              const SizedBox(height: 8),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.end,
                                children: [
                                  Text(
                                    snapshot.data![index].dataRichesta,
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
