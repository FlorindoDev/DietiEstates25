class Ricerca {
  final int idricerca;
  final String comando;
  final int idacquirente;

  Ricerca({
    required this.idricerca,
    required this.comando,
    required this.idacquirente
  });

  // Costruttore da JSON
  factory Ricerca.fromJson(Map<String, dynamic> json) {
    return Ricerca(
      idricerca: json['idricerca'],
      comando: json['comando'],
      idacquirente: json['idacquirente'],
     
    );
  }

  // Conversione in JSON
  Map<String, dynamic> toJson() {
    return {
      'idricerca': idricerca,
      'comando': comando,
      'idacquirente': idacquirente,
    };
  }
}
