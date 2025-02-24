package org.md.Estate;

import org.md.Agency.Agency;
import org.md.Estate.ClasseEnergetica.EnergeticClass;
import org.md.Estate.Mode.Mode;
import org.md.Estate.Status.Status;
import org.md.Serializzazione.Translate;
import org.md.Utente.Agent;

public class Indirizzo extends Translate {

    private String idIndirizzo = "";
    private String stato = "";
    private String cittá = "";
    private String via = "";
    private String numeroCivico = "";
    private int CAP = 0;


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

    public String getCittá() {
        return cittá;
    }

    public void setCittá(String cittá) {
        this.cittá = cittá;
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

    public int getCAP() {
        return CAP;
    }

    public void setCAP(int CAP) {
        this.CAP = CAP;
    }

    public Indirizzo() {}

    public Indirizzo(Indirizzo.Builder builder) {
        this.idIndirizzo = builder.idIndirizzo;
        this.CAP = builder.CAP;
        this.cittá = builder.cittá;
        this.stato = builder.stato;
        this.numeroCivico = builder.numeroCivico;
        this.via = builder.via;


    }

    public static class Builder<typeBuilder extends Indirizzo.Builder<typeBuilder>>{
        private String idIndirizzo = "";
        private String stato = "";
        private String cittá = "";
        private String via = "";
        private String numeroCivico = "";
        private int CAP = 0;

        public Builder(int idIndirizzo) {
            this.idIndirizzo = String.valueOf(idIndirizzo);
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

        public typeBuilder setCittá(String cittá) {
            this.cittá = cittá;
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

        public typeBuilder setCAP(int CAP) {
            this.CAP = CAP;
            return self();
        }
    }
}
