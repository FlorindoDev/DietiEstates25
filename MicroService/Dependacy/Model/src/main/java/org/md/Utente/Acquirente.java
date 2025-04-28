package org.md.Utente;



public class Acquirente extends Utente{
    private Boolean notifyNewEstate;
    private Boolean changePriceNotify;


    public Acquirente(){}

    protected Acquirente(Builder builder) {
        super(builder);
        this.notifyNewEstate = builder.notifyNewEstate;
        this.changePriceNotify = builder.changePriceNotify;
    }

    public Boolean getNotifyNewEstate() {
        return notifyNewEstate;
    }

    public Boolean getChangePriceNotify() {
        return changePriceNotify;
    }

    public void setNotifyNewEstate(Boolean notifyNewEstate) {
        this.notifyNewEstate = notifyNewEstate;
    }

    public void setChangePriceNotify(Boolean changePriceNotify) {
        this.changePriceNotify = changePriceNotify;
    }

    public static class Builder extends Utente.Builder<Acquirente.Builder>{

        private Boolean notifyNewEstate = null;
        private Boolean changePriceNotify = null;

        public Builder(int iduser, String email) {
            super(iduser, email);
        }

        public Builder setNotifyNewEstate(Boolean notifyNewEstate){
            this.notifyNewEstate = notifyNewEstate;
            return this;
        }

        public Builder setChangePriceNotify(Boolean changePriceNotify){
            this.changePriceNotify = changePriceNotify;
            return this;
        }

        @Override
        public Acquirente build(){
            return new Acquirente(this);
        }



    }
}
