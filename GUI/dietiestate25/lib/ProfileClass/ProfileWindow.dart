import 'package:flutter/material.dart';

class ProfileWindow extends StatefulWidget {

  @override
  State<ProfileWindow> createState() => _ProfileWindowState();

}

class _ProfileWindowState extends State<ProfileWindow>{

  @override
  Widget build(BuildContext context) {
    return Center(child: Text('Profile Page', style: TextStyle(fontSize: 24)));
  }

}