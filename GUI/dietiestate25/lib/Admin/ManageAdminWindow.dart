import 'dart:convert';
import 'dart:typed_data';

import 'package:dietiestate25/Admin/AdminHomeController.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';

class ManageAdminWindow extends StatefulWidget {

  const ManageAdminWindow({super.key, required this.appbar});

  final AppBar appbar;

  @override
  _ManageAdminWindowState createState() => _ManageAdminWindowState();
}

class _ManageAdminWindowState extends State<ManageAdminWindow> {
  
  late Future<List<Amministratore>> amministratori;
  late ScrollController _scrollController;

  bool isScrolled = false;

  void reload() {
    setState(() {
      amministratori = AdminHomeController.getAmministratori(context, "");
    });
  }

  @override
  void initState() {
    super.initState();
    
    amministratori = AdminHomeController.getAmministratori(context,"");
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
  
  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: widget.appbar,
      body: 
      Container(
        width: double.infinity,
        decoration: BoxDecoration(
          color: MyApp.celeste,
          borderRadius: BorderRadius.only(topLeft: Radius.circular(30), topRight: Radius.circular(30)),
        ),
        padding: const EdgeInsets.only(top: 20,left: 20, right: 20),
        child:
        FutureBuilder<List<Amministratore>>(
        future: amministratori,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
                    child: CircularProgressIndicator(strokeWidth: 5,
                      color: Colors.white,
                    ),
                  );
          } else if (snapshot.hasError) {
            WidgetsBinding.instance.addPostFrameCallback((_) {
                    MyApp.mostraPopUpWarining(
                        context, 'Error', "Errore riprova più tardi");
                  });
            return const Center();
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            WidgetsBinding.instance.addPostFrameCallback((_) {
                    MyApp.mostraPopUpWarining(
                        context, 'Error', "Nessun amministratore trovato");
                  });
            return const Center();
          } 
            List<Amministratore> amministratori = snapshot.data!;
            return ListView.builder(
              controller: _scrollController,
              itemCount: amministratori.length,
              itemBuilder: (context, index) {
                Amministratore admin = amministratori[index];
            
                return Container(
                  margin: const EdgeInsets.symmetric(vertical: 5),
                  decoration: BoxDecoration(
                    color: Colors.white, // Set your desired background color here
                    borderRadius: BorderRadius.circular(10),
                  ),
                  child: 
                  

                  
                  ListTile(
                    
                    title: Text('${admin.nome} ${admin.cognome}'),
                    subtitle: Text(admin.email),
                    leading: (admin.issupportoammi == true) ? const Icon(Icons.account_circle_rounded, color: MyApp.blu,) : const Icon(Icons.manage_accounts_rounded, color: MyApp.blu,),
                 
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                    trailing: MenuBar(
                      children:[
                        SubmenuButton(
                          menuChildren: [
                            MenuItemButton(
                              onPressed: () async {
                                await AdminHomeController.removeAdmin(context, admin, "");
                                reload();
                               },
                              child: const Text('Elimina'),
                            ),
                            MenuItemButton(
                              onPressed: (admin.issupportoammi == true) ? () async { 
                                await AdminHomeController.promoteAdmin(context, admin, "");
                                reload();
                              } : null,
                              child: const Text('Promuovi'),
                            ),
                            MenuItemButton(
                              onPressed: (admin.issupportoammi == false) ? () async { 
                                await AdminHomeController.unPromoteAdmin(context, admin, "");
                                reload();
                              } : null,
                              child: const Text('Retrocedi'),
                            ),
                          ],
                          child: const Icon(Icons.menu),
                        ), 
                      ], 
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