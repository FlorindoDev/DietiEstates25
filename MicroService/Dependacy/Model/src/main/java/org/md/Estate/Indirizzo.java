package org.md.Estate;


import org.md.Serializzazione.Translate;


public class Indirizzo extends Translate {

    private String idIndirizzo = "";
    private String stato = "";
    private String citta = "";
    private String via = "";
    private String numeroCivico = "";
    private int cap = 0;


    public String getIdIndirizzo() {
        return idIndirizzo;
    }

    public void setIdIndirizzo(String idIndirizzo) {
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

    public Indirizzo() {}

    public Indirizzo(Indirizzo.Builder builder) {
        this.idIndirizzo = builder.idIndirizzo;
        this.cap = builder.cap;
        this.citta = builder.citta;
        this.stato = builder.stato;
        this.numeroCivico = builder.numeroCivico;
        this.via = builder.via;


    }

    public static class Builder<typeBuilder extends Indirizzo.Builder<typeBuilder>>{
        private String idIndirizzo = "";
        private String stato = "";
        private String citta = "";
        private String via = "";
        private String numeroCivico = "";
        private int cap = 0;

        public Builder(String idIndirizzo) {
            this.idIndirizzo = idIndirizzo;
        }

        protected typeBuilder self(){
            return (typeBuilder)this;
        }

        public Indirizzo build(){
            return new Indirizzo(this);
        }


        public typeBuilder setIdIndirizzo(String idIndirizzo) {
            this.idIndirizzo = idIndirizzo;
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
    }
}
