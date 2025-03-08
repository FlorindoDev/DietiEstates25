import 'package:dietiestate25/Model/Appointment/Appointment.dart';
import 'package:dietiestate25/Model/Notify/Notify.dart';
import 'package:dietiestate25/Home/HomeController.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';

class CalendarWindow extends StatefulWidget {
  @override
  State<CalendarWindow> createState() => _CalendarWindowState();
}

class _CalendarWindowState extends State<CalendarWindow> {
  late Future<List<Appointment>> Appointments;

  void initState() {
    super.initState();
    Appointments = HomeController.getAppointment(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder(
          future: Appointments,
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              return ListView.builder(
                itemCount: snapshot.data!.length,
                itemBuilder: (context, index) {
                  return Padding(
                    padding: EdgeInsets.all(8),
                    //child: Text('[$index] ${snapshot.data![0].message}'),
                    child: Card(
                      elevation: 10.0,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(25),
                        side: BorderSide(color: MyApp.blu, width: 2),
                      ),

                      // Se vuoi togliere l’ombra del Card, puoi impostare elevation a 0
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
                                    style: TextStyle(
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
                                        color: Color.fromARGB(255, 0, 0, 0),
                                        width: 3,
                                      ),
                                    ),
                                    child: snapshot.data![index].icon()),
                              ],
                            ),
                            const SizedBox(height: 8),
                            Row(
                              children: [
                                Text(
                                  'Data appuntamento: ' + snapshot.data![index].data,
                                  style: TextStyle(fontSize: 14),
                                ),
                              ],
                            ),
                            const SizedBox(height: 8),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.end,
                              children: [
                                Text(
                                  snapshot.data![index].dataRichesta,
                                  style: TextStyle(fontSize: 14, color: Colors.grey),
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
            } else if (snapshot.hasError) {
              return Text("errore");
            }
            return Center(
              child: CircularProgressIndicator(
                strokeWidth: 5,
              ),
            );
          }),
    );
  }
}
