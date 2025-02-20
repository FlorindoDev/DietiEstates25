import 'package:flutter/material.dart';

class CalendarWindow extends StatefulWidget {

  @override
  State<CalendarWindow> createState() => _CalendarWindowState();

}

class _CalendarWindowState extends State<CalendarWindow>{

  @override
  Widget build(BuildContext context) {
    return Center(child: Text('Appuntamenti Page', style: TextStyle(fontSize: 24)));
  }

}