import 'package:dietiestate25/Model/Agenzia/Agenzia.dart';
import 'package:dietiestate25/Model/Utente/Utente.dart';
import 'package:dietiestate25/Model/Estate/Indirizzo.dart';

class Estate {
  int idEstate = 0;
  AgenteImmobiliare? agente;
  Indirizzo? indirizzo;
  Agenzia? agenzia;
  List<String>? foto;
  String descrizione = "";
  double? price;
  double? space;
  int? rooms;
  int? floor;
  int? wc;
  int? garage;
  bool? elevator;
  String? classeEnergetica;
  String? mode;
  String? stato;
  

  Map<String, dynamic> toJson() {
    return {
      'idEstate': idEstate,
      'foto': foto,
      'descrizione': descrizione,
      'price': price,
      'space': space,
      'rooms': rooms,
      'floor': floor,
      'wc': wc,
      'garage': garage,
      'elevator': elevator,
      'agenzia' : agenzia?.toJson(),
      'agente' : agente?.toJson(),
      'stato' : { 'name': stato},
      'mode' : { 'name': mode},
      'classeEnergetica' : { 'nome': classeEnergetica},
      'indirizzo' : indirizzo?.toJson(),

    };
  }

  factory Estate.fromJson(Map<String, dynamic> json) {
    Estate estate = Estate(
      idEstate: json['idEstate'],
      
      descrizione: json['descrizione'],
      price: json['price'],
      space: json['space'],
      rooms: json['rooms'],
      floor: json['floor'],
      wc: json['wc'],
      garage: json['garage'],
      elevator: json['elevator'],
      agenzia: Agenzia.fromJson(json['agenzia']),
      agente: AgenteImmobiliare.fromJson(json['agente']) ,
      stato : json['stato']['name'],
      mode : json['mode']['name'],
      classeEnergetica : json['classeEnergetica']['nome'],
      indirizzo : Indirizzo.fromJson(json['indirizzo'])
    );
    
    estate.foto = List.from(json['foto']);

    return estate;
  }

  Estate(
    {required this.idEstate, 
    //  required this.foto, 
     required this.descrizione, 
     required this.price, 
     required this.space,
     required this.rooms, 
     required this.floor,
     required this.wc, 
     required this.garage, 
     required this.elevator, 
     required this.agenzia, 
     required this.agente, 
     required this.stato, 
     required this.mode, 
     required this.classeEnergetica,
    required this.indirizzo
  });
  

  Estate._builder(EstateBuilder builder)
      : idEstate = builder.idEstateBuilder,
        agente = builder.agenteBuilder,
        indirizzo = builder.indirizzoBuilder,
        agenzia = builder.agenziaBuilder,
        foto = builder.fotoBuilder,
        descrizione = builder.descrizioneBuilder,
        price = builder.priceBuilder,
        space = builder.spaceBuilder,
        rooms = builder.roomsBuilder,
        floor = builder.floorBuilder,
        wc = builder.wcBuilder,
        garage = builder.garageBuilder,
        elevator = builder.elevatorBuilder,
        classeEnergetica = builder.classeEnergeticaBuilder,
        mode = builder.modeBuilder,
        stato = builder.statoBuilder;

  @override
  String toString() {
    return '''
Estate Details:
ID Estate: $idEstate
Agente: ${agente ?? "N/A"}
Indirizzo: ${indirizzo ?? "N/A"}
Agenzia: ${agenzia ?? "N/A"}
Foto: $foto
Descrizione: $descrizione
Prezzo: $price
Spazio: $space
Stanze: $rooms
Piano: $floor
Bagni: $wc
Garage: $garage
Ascensore: ${elevator}
Classe Energetica: ${classeEnergetica ?? "N/A"}
Modalità: ${mode ?? "N/A"}
Stato: ${stato ?? "N/A"}
''';
  }
}

class EstateBuilder {
  int idEstateBuilder;
  AgenteImmobiliare? agenteBuilder;
  Indirizzo? indirizzoBuilder;
  Agenzia? agenziaBuilder;
  List<String>? fotoBuilder;
  String descrizioneBuilder = "";
  double priceBuilder = 0;
  double spaceBuilder = 0;
  int roomsBuilder = 0;
  int floorBuilder = 0;
  int wcBuilder = 0;
  int garageBuilder = 0;
  bool elevatorBuilder = false;
  String? classeEnergeticaBuilder;
  String? modeBuilder;
  String? statoBuilder;

  EstateBuilder(this.idEstateBuilder);

  EstateBuilder setAgenteBuilder(AgenteImmobiliare agente) {
    agenteBuilder = agente;
    return this;
  }

  EstateBuilder setIndirizzoBuilder(Indirizzo indirizzo) {
    indirizzoBuilder = indirizzo;
    return this;
  }

  EstateBuilder setAgenziaBuilder(Agenzia agenzia) {
    agenziaBuilder = agenzia;
    return this;
  }

  EstateBuilder setFotoBuilder(List<String> foto) {
    fotoBuilder = foto;
    return this;
  }

  EstateBuilder setDescrizioneBuilder(String descrizione) {
    descrizioneBuilder = descrizione;
    return this;
  }

  EstateBuilder setPriceBuilder(double price) {
    priceBuilder = price;
    return this;
  }

  EstateBuilder setSpaceBuilder(double space) {
    spaceBuilder = space;
    return this;
  }

  EstateBuilder setRoomsBuilder(int rooms) {
    roomsBuilder = rooms;
    return this;
  }

  EstateBuilder setFloorBuilder(int floor) {
    floorBuilder = floor;
    return this;
  }

  EstateBuilder setWcBuilder(int wc) {
    wcBuilder = wc;
    return this;
  }

  EstateBuilder setGarageBuilder(int garage) {
    garageBuilder = garage;
    return this;
  }

  EstateBuilder setElevatorBuilder(bool elevator) {
    elevatorBuilder = elevator;
    return this;
  }

  EstateBuilder setClasseEnergeticaBuilder(String classe) {
    classeEnergeticaBuilder = classe;
    return this;
  }

  EstateBuilder setModeBuilder(String mode) {
    modeBuilder = mode;
    return this;
  }

  EstateBuilder setStatoBuilder(String stato) {
    statoBuilder = stato;
    return this;
  }

  Estate build() {
    return Estate._builder(this);
  }
}

