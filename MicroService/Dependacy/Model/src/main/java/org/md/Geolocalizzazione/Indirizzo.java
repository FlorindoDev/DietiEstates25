package org.md.Geolocalizzazione;


import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import org.md.Serializzazione.Translate;


public class Indirizzo extends Translate {

    @QueryParam("codicePartitaIVA")
    @DefaultValue("0")
    private int idIndirizzo = 0;

    @QueryParam("stato")
    private String stato = "";

    @QueryParam("citta")
    private String citta = "";

    @QueryParam("quartiere")
    private String quartiere = "";

    @QueryParam("via")
    private String via = "";

    @QueryParam("numeroCivico")
    private String numeroCivico = "";

    @QueryParam("cap")
    private int cap = 0;

    @QueryParam("latitudine")
    private Double latitude = null;

    @QueryParam("longitudine")
    private Double longitude = null;


    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public int getIdIndirizzo() {
        return idIndirizzo;
    }

    public void setIdIndirizzo(int idIndirizzo) {
        this.idIndirizzo = idIndirizzo;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getQuartiere() {
        return quartiere;
    }

    public void setQuartiere(String quartiere) {
        this.quartiere = quartiere;
    }

    public Indirizzo() {}

    public Indirizzo(Indirizzo.Builder builder) {
        this.idIndirizzo = builder.idIndirizzo;
        this.cap = builder.cap;
        this.citta = builder.citta;
        this.quartiere = builder.quartiere;
        this.stato = builder.stato;
        this.numeroCivico = builder.numeroCivico;
        this.via = builder.via;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;

    }

    public static class Builder<typeBuilder extends Indirizzo.Builder<typeBuilder>>{
        private int idIndirizzo = 0;
        private String stato = "";
        private String citta = "";
        private String quartiere = "";
        private String via = "";
        private String numeroCivico = "";
        private int cap = 0;
        private Double latitude = null;
        private Double longitude = null;


        public Builder(int idIndirizzo) {
            this.idIndirizzo = idIndirizzo;
        }

        protected typeBuilder self(){
            return (typeBuilder)this;
        }

        public Indirizzo build(){
            return new Indirizzo(this);
        }


        public typeBuilder setIdIndirizzo(int idIndirizzo) {
            this.idIndirizzo = idIndirizzo;
            return self();
        }

        public typeBuilder setQuartiere(String quartiere) {
            this.quartiere = quartiere;
            return self();
        }

        public typeBuilder setStato(String stato) {
            this.stato = stato;
            return self();
        }

        public typeBuilder setCitta(String citta) {
            this.citta = citta;
            return self();
        }

        public typeBuilder setVia(String via) {
            this.via = via;
            return self();
        }

        public typeBuilder setNumeroCivico(String numeroCivico) {
            this.numeroCivico = numeroCivico;
            return self();
        }

        public typeBuilder setCap(int cap) {
            this.cap = cap;
            return self();
        }

        public typeBuilder setLatitude(Double latitude) {
            this.latitude = latitude;
            return self();
        }

        public typeBuilder setLongitude(Double longitude) {
            this.longitude = longitude;
            return self();
        }


    }
}
