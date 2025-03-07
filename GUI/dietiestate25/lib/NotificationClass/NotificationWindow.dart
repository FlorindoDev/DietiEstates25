import 'package:dietiestate25/Model/Notify/Notify.dart';
import 'package:dietiestate25/Home/HomeController.dart';
import 'package:flutter/material.dart';
import 'package:dietiestate25/main.dart';

class NotificationWindow extends StatefulWidget {
  @override
  State<NotificationWindow> createState() => _NotificationWindowState();
}

class _NotificationWindowState extends State<NotificationWindow> {
  late Future<List<Notify>> notifications;

  void initState() {
    super.initState();
    notifications = HomeController.getNotify();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder(
          future: notifications,
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
                                    snapshot.data![index].message,
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
                                    child: CircleAvatar(
                                      radius: 16,
                                      backgroundColor: snapshot.data![index].isRifiutato() ? MyApp.rosso : MyApp.celeste,
                                      child: Icon(
                                        snapshot.data![index].isRifiutato() ? Icons.close : Icons.check,
                                        color: Color.fromARGB(255, 0, 0, 0),
                                        size: 25,
                                      ),
                                    )),
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
                                  snapshot.data![index].dataRicezione,
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
              child: CircularProgressIndicator(),
            );
          }),
    );
  }
}
