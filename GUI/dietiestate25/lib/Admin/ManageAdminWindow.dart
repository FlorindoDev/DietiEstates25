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

  @override
  void initState() {
    super.initState();
    print("\n\n UEUEUEEUE \n\n");
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
      body: FutureBuilder<List<Amministratore>>(
        future: amministratori,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
                    child: CircularProgressIndicator(strokeWidth: 5),
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
                return ListTile(
                  title: Text(admin.nome + " " + admin.cognome),
                  subtitle: Text(admin.email),
                );
              },
            );
          
        },
      ),
    );
  }
}