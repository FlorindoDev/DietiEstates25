package org.md.Estate;

import org.md.Estate.ClasseEnergetica.EnergeticClass;
import org.md.Estate.Mode.Mode;
import org.md.Agency.Agency;
import org.md.Estate.Status.Status;
import org.md.Serializzazione.Translate;
import org.md.Utente.Agent;

public class Estate extends Translate {
    private int id_estate = 0;
    private Agent agente;
    private Indirizzo indirizzo;
    private Agency agenzia;
    private String foto = "";
    private String descrizione = "";
    private double price = 0;
    private double space = 0;
    private int rooms = 0;
    private int floor = 0;
    private int wc = 0;
    private int garage = 0;
    private boolean elevator = false;
    private EnergeticClass classeEnergetica;
    private Mode mode;
    private Status stato;

    public Estate() {}

    public Estate(Estate.Builder builder) {
        this.id_estate = builder.id_estate;
        this.agente = builder.agente;
        this.indirizzo = builder.indirizzo;
        this.agenzia = builder.agenzia;
        this.foto = builder.foto;
        this.descrizione = builder.descrizione;
        this.price = builder.price;
        this.space = builder.space;
        this.rooms = builder.rooms;
        this.floor = builder.floor;
        this.wc = builder.wc;
        this.garage = builder.garage;
        this.elevator = builder.elevator;
        this.classeEnergetica = builder.classeEnergetica;
        this.mode = builder.mode;
        this.stato = builder.stato;
    }

    public int getId_estate() {
        return id_estate;
    }

    public void setId_estate(int id_estate) {
        this.id_estate = id_estate;
    }

    public Agent getAgente() {
        return agente;
    }

    public void setAgente(Agent agente) {
        this.agente = agente;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(Indirizzo indirizzo) {
        this.indirizzo = indirizzo;
    }

    public Agency getAgenzia() {
        return agenzia;
    }

    public void setAgenzia(Agency agenzia) {
        this.agenzia = agenzia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getWc() {
        return wc;
    }

    public void setWc(int wc) {
        this.wc = wc;
    }

    public int getGarage() {
        return garage;
    }

    public void setGarage(int garage) {
        this.garage = garage;
    }

    public boolean isElevator() {
        return elevator;
    }

    public void setElevator(boolean elevator) {
        this.elevator = elevator;
    }

    public EnergeticClass getClasseEnergetica() {
        return classeEnergetica;
    }

    public void setClasseEnergetica(EnergeticClass classeEnergetica) {
        this.classeEnergetica = classeEnergetica;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Status getStato() {
        return stato;
    }

    public void setStato(Status stato) {
        this.stato = stato;
    }


    public static class Builder<typeBuilder extends Builder<typeBuilder>>{
        private int id_estate = 0;
        private Agent agente;
        private Indirizzo indirizzo;
        private Agency agenzia;
        private String foto = "";
        private String descrizione = "";
        private double price = 0;
        private double space = 0;
        private int rooms = 0;
        private int floor = 0;
        private int wc = 0;
        private int garage = 0;
        private boolean elevator = false;
        private EnergeticClass classeEnergetica;
        private Mode mode;
        private Status stato;

        public Builder(int id_estate) {
            this.id_estate = id_estate;
        }

//        public typeBuilder setId_estate(int id_estate) {
//            this.id_estate = id_estate;
//            return self();
//        }

        public typeBuilder setAgente(Agent agente) {
            this.agente = agente;
            return self();
        }

        public typeBuilder setIndirizzo(Indirizzo indirizzo) {
            this.indirizzo = indirizzo;
            return self();
        }

        public typeBuilder setAgenzia(Agency agenzia) {
            this.agenzia = agenzia;
            return self();
        }

        public typeBuilder setFoto(String foto) {
            this.foto = foto;
            return self();
        }

        public typeBuilder setDescrizione(String descrizione) {
            this.descrizione = descrizione;
            return self();
        }

        public typeBuilder setPrice(double price) {
            this.price = price;
            return self();
        }

        public typeBuilder setSpace(double space) {
            this.space = space;
            return self();
        }

        public typeBuilder setRooms(int rooms) {
            this.rooms = rooms;
            return self();
        }

        public typeBuilder setFloor(int floor) {
            this.floor = floor;
            return self();
        }

        public typeBuilder setWc(int wc) {
            this.wc = wc;
            return self();
        }

        public typeBuilder setGarage(int garage) {
            this.garage = garage;
            return self();
        }

        public typeBuilder setElevator(boolean elevator) {
            this.elevator = elevator;
            return self();
        }

        public typeBuilder setClasseEnergetica(EnergeticClass classeEnergetica) {
            this.classeEnergetica = classeEnergetica;
            return self();
        }

        public typeBuilder setMode(Mode mode) {
            this.mode = mode;
            return self();
        }

        public typeBuilder setStato(Status stato) {
            this.stato = stato;
            return self();
        }

        protected typeBuilder self(){
            return (typeBuilder)this;
        }

        public Estate build(){
            return new Estate(this);
        }


    }
}
