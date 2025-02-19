class Agenzia {
  String _id_agency = "";
  String _nome_agenzia = "";
  String _partitaIVA = "";
  String _sede = "";
  String _email = "";

  Agenzia._builder(AgenziaBuilder builder)
      : _id_agency = builder._id_agency,
        _nome_agenzia = builder._nome_agenzia ?? '',
        _partitaIVA = builder._partitaIVA ?? '',
        _sede = builder._sede ?? '',
        _email = builder._email ?? '';

  static AgenziaBuilder get builder => AgenziaBuilder();

  String get nome_agenzia => _nome_agenzia;

  set nome_agenzia(String value) {
    _nome_agenzia = value;
  }
  
  String get partitaIVA => _partitaIVA;

  set partitaIVA(String value) {
    _partitaIVA = value;
  }
  
  String get sede => _sede;

  set sede(String value) {
    _sede = value;
  }
  
  String get email => _email;

  set email(String value) {
    _email = value;
  }

  String get id_agency => _id_agency;

  set id_agency(String value) {
    _id_agency = value;
  }


}
class AgenziaBuilder{

  String _id_agency = "";
  String _nome_agenzia = "";
  String _partitaIVA = "";
  String _sede = "";
  String _email = "";

  AgenziaBuilder setId(String id_agency) {
    this._id_agency = id_agency;
    return this;
  }

  AgenziaBuilder setAgencyName(String nome_agenzia) {
    this._nome_agenzia = nome_agenzia;
    return this;
  }

  AgenziaBuilder setEmail(String email) {
    _email = email;
    return this;
  }

  AgenziaBuilder setPartitaIVA(String partitaIVA) {
    this._partitaIVA = partitaIVA;
    return this;
  }

  AgenziaBuilder setSede(String sede) {
    this._sede = sede;
    return this;
  }

  Agenzia build() {
    return Agenzia._builder(this);
  }

}