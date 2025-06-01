package org.md.Estate;

import org.md.Estate.ClasseEnergetica.EnergeticClass;

import org.md.Agency.Agency;
import org.md.Estate.Mode.Mode;
import org.md.Estate.Status.Status;
import org.md.Geolocalizzazione.Indirizzo;
import org.md.Serializzazione.Translate;
import org.md.Utente.Agent;

import java.util.Collections;
import java.util.List;

public class Estate extends Translate {
    private int idEstate = 0;
    private Agent agente;
    private Indirizzo indirizzo;
    private Agency agenzia;
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
    private List<String> foto;

    public Estate() {}

    public Estate(Estate.Builder builder) {
        this.idEstate = builder.idEstateBuilder;
        this.agente = builder.agenteBuilder;
        this.indirizzo = builder.indirizzoBuilder;
        this.agenzia = builder.agenziaBuilder;
        this.foto = builder.fotoBuilder;
        this.descrizione = builder.descrizioneBuilder;
        this.price = builder.priceBuilder;
        this.space = builder.spaceBuilder;
        this.rooms = builder.roomsBuilder;
        this.floor = builder.floorBuilder;
        this.wc = builder.wcBuilder;
        this.garage = builder.garageBuilder;
        this.elevator = builder.elevatorBuilder;
        this.classeEnergetica = builder.classeEnergeticaBuilder;
        this.mode = builder.modeBuilder;
        this.stato = builder.statoBuilder;
    }

    public int getIdEstate() {
        return idEstate;
    }

    public void setIdEstate(int idEstate) {
        this.idEstate = idEstate;
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

    public List<String> getFoto() {
        return foto;
    }

    public void setFoto(List<String> foto) {
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

    public boolean getElevator(){
        return elevator;
    }

    public void setGarage(int garage) {
        this.garage = garage;
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

    @Override
    public String toString() {
        return "Estate Details:\n" +
                "ID Estate: " + idEstate + "\n" +
                "Agente: " + (agente != null ? agente.toString() : "N/A") + "\n" +
                "Indirizzo: " + (indirizzo != null ? indirizzo.toString() : "N/A") + "\n" +
                "Agenzia: " + (agenzia != null ? agenzia.toString() : "N/A") + "\n" +
                "Foto: " + foto + "\n" +
                "Descrizione: " + descrizione + "\n" +
                "Prezzo: " + price + "\n" +
                "Spazio: " + space + "\n" +
                "Stanze: " + rooms + "\n" +
                "Piano: " + floor + "\n" +
                "Bagni: " + wc + "\n" +
                "Garage: " + garage + "\n" +
                "Ascensore: " + (elevator ? "Sì" : "No") + "\n" +
                "Classe Energetica: " + (classeEnergetica != null ? classeEnergetica.toString() : "N/A") + "\n" +
                "Modalità: " + (mode != null ? mode.toString() : "N/A") + "\n" +
                "Stato: " + (stato != null ? stato.toString() : "N/A");
    }

    public static class Builder<typeBuilder extends Builder<typeBuilder>>{
        private int idEstateBuilder = 0;
        private Agent agenteBuilder;
        private Indirizzo indirizzoBuilder;
        private Agency agenziaBuilder;
        private List<String> fotoBuilder;
        private String descrizioneBuilder = "";
        private double priceBuilder = 0;
        private double spaceBuilder = 0;
        private int roomsBuilder = 0;
        private int floorBuilder = 0;
        private int wcBuilder = 0;
        private int garageBuilder = 0;
        private boolean elevatorBuilder = false;
        private EnergeticClass classeEnergeticaBuilder;
        private Mode modeBuilder;
        private Status statoBuilder;

        public Builder(int idEstateBuilder) {
            this.idEstateBuilder = idEstateBuilder;
        }


        public typeBuilder setAgenteBuilder(Agent agenteBuilder) {
            this.agenteBuilder = agenteBuilder;
            return self();
        }

        public typeBuilder setIndirizzoBuilder(Indirizzo indirizzoBuilder) {
            this.indirizzoBuilder = indirizzoBuilder;
            return self();
        }

        public typeBuilder setAgenziaBuilder(Agency agenziaBuilder) {
            this.agenziaBuilder = agenziaBuilder;
            return self();
        }

        public typeBuilder setFotoBuilder(List<String> fotoBuilder) {
            this.fotoBuilder = fotoBuilder;
            return self();
        }

        public typeBuilder setDescrizioneBuilder(String descrizioneBuilder) {
            this.descrizioneBuilder = descrizioneBuilder;
            return self();
        }

        public typeBuilder setPriceBuilder(double priceBuilder) {
            this.priceBuilder = priceBuilder;
            return self();
        }

        public typeBuilder setSpaceBuilder(double spaceBuilder) {
            this.spaceBuilder = spaceBuilder;
            return self();
        }

        public typeBuilder setRoomsBuilder(int roomsBuilder) {
            this.roomsBuilder = roomsBuilder;
            return self();
        }

        public typeBuilder setFloorBuilder(int floorBuilder) {
            this.floorBuilder = floorBuilder;
            return self();
        }

        public typeBuilder setWcBuilder(int wcBuilder) {
            this.wcBuilder = wcBuilder;
            return self();
        }

        public typeBuilder setGarageBuilder(int garageBuilder) {
            this.garageBuilder = garageBuilder;
            return self();
        }

        public typeBuilder setElevatorBuilder(boolean elevatorBuilder) {
            this.elevatorBuilder = elevatorBuilder;
            return self();
        }

        public typeBuilder setClasseEnergeticaBuilder(EnergeticClass classeEnergeticaBuilder) {
            this.classeEnergeticaBuilder = classeEnergeticaBuilder;
            return self();
        }

        public typeBuilder setModeBuilder(Mode modeBuilder) {
            this.modeBuilder = modeBuilder;
            return self();
        }

        public typeBuilder setStatoBuilder(Status statoBuilder) {
            this.statoBuilder = statoBuilder;
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
