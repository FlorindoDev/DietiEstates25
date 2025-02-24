package org.md.Appointment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.md.Estate.Estate;
import org.md.Serializzazione.Translate;
import org.md.Utente.Acquirente;
import org.md.Utente.Agent;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") // Specifica il tipo nel JSON
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = AppointmentPending.class, name = "AppointmentPending"),
//        @JsonSubTypes.Type(value = AppointmentAccept.class, name = "AppointmentAccept"),
//        @JsonSubTypes.Type(value = AppointmentReject.class, name = "AppointmentReject")
//})
public class Appointment extends Translate {

    protected int id_appointment;
    protected String data;
    protected Acquirente acquirente;
    protected Estate estate;

    //Costrutore vuoto perchè è neccessario per JAX-RS
    public Appointment() {}

    public Appointment(Builder builder) {
        this.id_appointment = builder.id_appointment;
        this.data = builder.data;
        this.acquirente = builder.acquirente;
        this.estate = builder.estate;
    }

    public void setId_appointment(int id_appointment) {
        this.id_appointment = id_appointment;
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

    public int getId_appointment() {
        return id_appointment;
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

        protected int id_appointment;
        protected String data = "";
        protected Acquirente acquirente = null;
        protected Estate estate = null;

        public Builder(int id_appointment){
            this.id_appointment = id_appointment;
        }

        public TypeBuilder setData(String data){
            this.data = data;
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
