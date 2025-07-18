class Indirizzo {
  final int idIndirizzo;
  final String stato;
  final String citta;
  final String? quartiere;
  final String via;
  final String numeroCivico;
  final int cap;
  final double latitudine;
  final double logitudine;

  Indirizzo({
    required this.idIndirizzo,
    required this.stato,
    required this.citta,
    this.quartiere,
    required this.via,
    required this.numeroCivico,
    required this.cap,
    required this.latitudine,
    required this.logitudine,
  });

  // Costruttore da JSON
  factory Indirizzo.fromJson(Map<String, dynamic> json) {
    return Indirizzo(
      idIndirizzo: json['idIndirizzo'],
      stato: json['stato'],
      citta: json['citta'],
      quartiere: json['quartiere'],
      via: json['via'],
      numeroCivico: json['numeroCivico'],
      cap: json['cap'],
      latitudine: json['latitude'],
      logitudine: json['longitude'],
    );
  }

  // Conversione in JSON
  Map<String, dynamic> toJson() {
    return {
      'idIndirizzo': idIndirizzo,
      'stato': stato,
      'citta': citta,
      'quartiere': quartiere,
      'via': via,
      'numeroCivico': numeroCivico,
      'cap': cap,
      'longitude': logitudine,
      'latitude': latitudine
    };
  }
}
