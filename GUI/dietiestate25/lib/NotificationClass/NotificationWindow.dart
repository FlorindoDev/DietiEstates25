import 'package:dietiestate25/Model/Notify/Notify.dart';
import 'package:dietiestate25/Home/HomeController.dart';
import 'package:flutter/material.dart';

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
                    child: Text('[$index] ${snapshot.data![0].message}'),
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
