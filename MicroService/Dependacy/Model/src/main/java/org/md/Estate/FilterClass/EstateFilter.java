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
    private String citta = "";

    public EstateFilter() {}

    public EstateFilter(Builder builder) {
        this.circumference = builder.circumferenceBuilder;
        this.mode = builder.modeBuilder;
        this.energeticClass = builder.energeticClassBuilder;
        this.wc = builder.wcBuilder;
        this.stato = builder.statoBuilder;
        this.piano = builder.pianoBuilder;
        this.garages = builder.garagesBuilder;
        this.ascensore = builder.ascensoreBuilder;
        this.maxRooms = builder.maxRoomsBuilder;
        this.minRooms = builder.minRoomsBuilder;
        this.maxSpace = builder.maxSpaceBuilder;
        this.minSpace = builder.minSpaceBuilder;
        this.maxPrice = builder.maxPriceBuilder;
        this.minPrice = builder.minPriceBuilder;
        this.descrizione = builder.descrizioneBuilder;
        this.idEstate = builder.idEstateBuilder;
        this.citta = builder.cittaBuilder;

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

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public static class Builder<typeBuilder extends EstateFilter.Builder<typeBuilder>>{
        private int idEstateBuilder = 0;
        private String descrizioneBuilder = "";
        private double minPriceBuilder = 0;
        private double maxPriceBuilder = 0;
        private double minSpaceBuilder = 0;
        private double maxSpaceBuilder = 0;
        private double minRoomsBuilder = 0;
        private double maxRoomsBuilder = 0;
        private boolean ascensoreBuilder = false;
        private int garagesBuilder = 0;
        private int pianoBuilder = 0;
        private Status statoBuilder = null;
        private int wcBuilder = 0;
        private EnergeticClass energeticClassBuilder = null;
        private Mode modeBuilder = null;
        private Circumference circumferenceBuilder = null;
        private String cittaBuilder = "";

        public Builder(int idEstate) {
            this.idEstateBuilder = idEstate;
        }

        protected typeBuilder self(){
            return (typeBuilder)this;
        }

        public EstateFilter build(){
            return new EstateFilter(this);
        }


        public typeBuilder setIdEstateBuilder(int idEstateBuilder) {
            this.idEstateBuilder = idEstateBuilder;
            return self();
        }

        public typeBuilder setCircumferenceBuilder(Circumference circumferenceBuilder) {
            this.circumferenceBuilder = circumferenceBuilder;
            return self();
        }

        public typeBuilder setDescrizioneBuilder(String descrizioneBuilder) {
            this.descrizioneBuilder = descrizioneBuilder;
            return self();
        }

        public typeBuilder setCittaBuilder(String cittaBuilder) {
            this.cittaBuilder = cittaBuilder;
            return self();
        }

        public typeBuilder setMinPriceBuilder(double minPriceBuilder) {
            this.minPriceBuilder = minPriceBuilder;
            return self();
        }

        public typeBuilder setMaxPriceBuilder(double maxPriceBuilder) {
            this.maxPriceBuilder = maxPriceBuilder;
            return self();
        }

        public typeBuilder setMinSpaceBuilder(double minSpaceBuilder) {
            this.minSpaceBuilder = minSpaceBuilder;
            return self();
        }

        public typeBuilder setMaxSpaceBuilder(double maxSpaceBuilder) {
            this.maxSpaceBuilder = maxSpaceBuilder;
            return self();
        }

        public typeBuilder setMinRoomsBuilder(double minRoomsBuilder) {
            this.minRoomsBuilder = minRoomsBuilder;
            return self();
        }

        public typeBuilder setMaxRoomsBuilder(double maxRoomsBuilder) {
            this.maxRoomsBuilder = maxRoomsBuilder;
            return self();
        }

        public typeBuilder setAscensoreBuilder(boolean ascensoreBuilder) {
            this.ascensoreBuilder = ascensoreBuilder;
            return self();
        }

        public typeBuilder setGaragesBuilder(int garagesBuilder) {
            this.garagesBuilder = garagesBuilder;
            return self();
        }

        public typeBuilder setPianos(int piano) {
            this.pianoBuilder = piano;
            return self();
        }

        public typeBuilder setStatoBuilder(Status statoBuilder) {
            this.statoBuilder = statoBuilder;
            return self();
        }

        public typeBuilder setWcBuilder(int wcBuilder) {
            this.wcBuilder = wcBuilder;
            return self();
        }

        public typeBuilder setEnergeticClassBuilder(EnergeticClass energeticClassBuilder) {
            this.energeticClassBuilder = energeticClassBuilder;
            return self();
        }

        public typeBuilder setModeBuilder(Mode modeBuilder) {
            this.modeBuilder = modeBuilder;
            return self();
        }
    }
}
