import 'package:flutter/material.dart';

class NotificationWindow extends StatefulWidget {

  @override
  State<NotificationWindow> createState() => _NotificationWindowState();

}

class _NotificationWindowState extends State<NotificationWindow>{

  @override
  Widget build(BuildContext context) {
    return Center(child: Text('Notification Page', style: TextStyle(fontSize: 24)));
  }

}