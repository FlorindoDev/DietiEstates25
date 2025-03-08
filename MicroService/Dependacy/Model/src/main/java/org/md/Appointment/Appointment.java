package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.md.Estate.Estate;
import org.md.Serializzazione.Translate;
import org.md.Utente.Acquirente;

public class Appointment extends Translate {

    protected int idAppointment;
    protected String data;
    protected String esito;
    protected String dataRichesta;
    protected Acquirente acquirente;
    protected Estate estate;

    //Costrutore vuoto perchè è neccessario per JAX-RS
    public Appointment() {}

    public Appointment(Builder builder) {
        this.idAppointment = builder.idAppointment;
        this.data = builder.data;
        this.acquirente = builder.acquirente;
        this.estate = builder.estate;
        this.dataRichesta = builder.dataRichesta;
        this.esito = builder.esito;
    }

    public String getDataRichesta() {
        return dataRichesta;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public void setDataRichesta(String dataRichesta) {
        this.dataRichesta = dataRichesta;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setAcquirente(Acquirente acquirente) {
        this.acquirente = acquirente;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public int getIdAppointment() {
        return idAppointment;
    }

    public String getData() {
        return data;
    }

    public Acquirente getAcquirente() {
        return acquirente;
    }

    public Estate getEstate() {
        return estate;
    }

    @JsonIgnore
    public String getName(){return "Appointment";}

    public static class Builder<TypeBuilder extends Builder<TypeBuilder>>{

        protected int idAppointment;
        protected String data = "";
        protected Acquirente acquirente = null;
        protected Estate estate = null;
        protected String esito = "";

        protected String dataRichesta = "";

        public Builder(int idAppointment){
            this.idAppointment = idAppointment;
        }

        public TypeBuilder setData(String data){
            this.data = data;
            return self();
        }

        public TypeBuilder setEsito(String esito){
            this.esito = esito;
            return self();
        }

        public TypeBuilder setdataRichesta(String dataRichesta){
            this.dataRichesta = dataRichesta;
            return self();
        }

        public TypeBuilder setEstate(Estate estate){
            this.estate = estate;
            return self();
        }

        public TypeBuilder setAcquirente(Acquirente acquirente){
            this.acquirente = acquirente;
            return self();
        }

        public Appointment build(){
            return new Appointment(this);
        }

        protected TypeBuilder self(){
            return (TypeBuilder)this;
        }

    }

}
