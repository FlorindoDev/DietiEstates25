package org.md.Estate.FilterClass;


import org.md.Estate.ClasseEnergetica.EnergeticClass;
import org.md.Estate.Mode.Mode;
import org.md.Estate.Status.Status;
import org.md.Geolocalizzazione.Circumference;
import org.md.Serializzazione.Translate;

public class EstateFilter extends Translate {
    private int idEstate = 0;
    private String descrizione = "";
    private double minPrice = 0;
    private double maxPrice = 0;
    private double minSpace = 0;
    private double maxSpace = 0;
    private double minRooms = 0;
    private double maxRooms = 0;
    private boolean ascensore = false;
    private int garages = 0;
    private int piano = 0;
    private Status stato = null;
    private int wc = 0;
    private EnergeticClass energeticClass = null;
    private Mode mode = null;
    private Circumference circumference= null;

    public EstateFilter() {}

    public EstateFilter(Builder builder) {
        this.idEstate = builder.idEstate;
        this.descrizione = builder.descrizione;
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;
        this.minSpace = builder.minSpace;
        this.maxSpace = builder.maxSpace;
        this.minRooms = builder.minRooms;
        this.maxRooms = builder.maxRooms;
        this.ascensore = builder.ascensore;
        this.garages = builder.garages;
        this.piano = builder.piano;
        this.stato = builder.stato;
        this.wc = builder.wc;
        this.energeticClass = builder.energeticClass;
        this.mode = builder.mode;
        this.circumference = builder.circumference;

    }

    public int getIdEstate() {
        return idEstate;
    }

    public void setIdEstate(int idEstate) {
        this.idEstate = idEstate;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinSpace() {
        return minSpace;
    }

    public void setMinSpace(double minSpace) {
        this.minSpace = minSpace;
    }

    public double getMaxSpace() {
        return maxSpace;
    }

    public void setMaxSpace(double maxSpace) {
        this.maxSpace = maxSpace;
    }

    public double getMinRooms() {
        return minRooms;
    }

    public void setMinRooms(double minRooms) {
        this.minRooms = minRooms;
    }

    public double getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(double maxRooms) {
        this.maxRooms = maxRooms;
    }

    public boolean isAscensore() {
        return ascensore;
    }

    public void setAscensore(boolean ascensore) {
        this.ascensore = ascensore;
    }

    public int getGarages() {
        return garages;
    }

    public void setGarages(int garages) {
        this.garages = garages;
    }

    public int getPiano() {
        return piano;
    }

    public void setPiano(int piano) {
        this.piano = piano;
    }

    public Status getStato() {
        return stato;
    }

    public void setStato(Status stato) {
        this.stato = stato;
    }

    public int getWc() {
        return wc;
    }

    public void setWc(int wc) {
        this.wc = wc;
    }

    public EnergeticClass getEnergeticClass() {
        return energeticClass;
    }

    public void setEnergeticClass(EnergeticClass energeticClass) {
        this.energeticClass = energeticClass;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Circumference getCircumference() {
        return circumference;
    }

    public void setCircumference(Circumference circumference) {
        this.circumference = circumference;
    }

    public static class Builder<typeBuilder extends EstateFilter.Builder<typeBuilder>>{
        private int idEstate = 0;
        private String descrizione = "";
        private double minPrice = 0;
        private double maxPrice = 0;
        private double minSpace = 0;
        private double maxSpace = 0;
        private double minRooms = 0;
        private double maxRooms = 0;
        private boolean ascensore = false;
        private int garages = 0;
        private int piano = 0;
        private Status stato = null;
        private int wc = 0;
        private EnergeticClass energeticClass = null;
        private Mode mode = null;
        private Circumference circumference= null;

        public Builder(int idEstate) {
            this.idEstate = idEstate;
        }

        protected typeBuilder self(){
            return (typeBuilder)this;
        }

        public EstateFilter build(){
            return new EstateFilter(this);
        }


        public typeBuilder setIdEstate(int idEstate) {
            this.idEstate = idEstate;
            return self();
        }

        public typeBuilder setCircumference(Circumference circumference) {
            this.circumference = circumference;
            return self();
        }

        public typeBuilder setDescrizione(String descrizione) {
            this.descrizione = descrizione;
            return self();
        }

        public typeBuilder setMinPrice(double minPrice) {
            this.minPrice = minPrice;
            return self();
        }

        public typeBuilder setMaxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
            return self();
        }

        public typeBuilder setMinSpace(double minSpace) {
            this.minSpace = minSpace;
            return self();
        }

        public typeBuilder setMaxSpace(double maxSpace) {
            this.maxSpace = maxSpace;
            return self();
        }

        public typeBuilder setMinRooms(double minRooms) {
            this.minRooms = minRooms;
            return self();
        }

        public typeBuilder setMaxRooms(double maxRooms) {
            this.maxRooms = maxRooms;
            return self();
        }

        public typeBuilder setAscensore(boolean ascensore) {
            this.ascensore = ascensore;
            return self();
        }

        public typeBuilder setGarages(int garages) {
            this.garages = garages;
            return self();
        }

        public typeBuilder setPianos(int piano) {
            this.piano = piano;
            return self();
        }

        public typeBuilder setStato(Status stato) {
            this.stato = stato;
            return self();
        }

        public typeBuilder setWc(int wc) {
            this.wc = wc;
            return self();
        }

        public typeBuilder setEnergeticClass(EnergeticClass energeticClass) {
            this.energeticClass = energeticClass;
            return self();
        }

        public typeBuilder setMode(Mode mode) {
            this.mode = mode;
            return self();
        }
    }
}
