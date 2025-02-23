package org.md.Utente;

import org.md.Utente.interfacce.UserSeparators;

public class Acquirente extends Utente{
    private Boolean notify_new_estate;
    private Boolean change_price_notify;

    public Acquirente(){}

    public Acquirente(Builder builder) {
        super(builder);
        this.notify_new_estate = builder.notify_new_estate;
        this.change_price_notify = builder.change_price_notify;
    }

    public void Separator(UserSeparators split){
        split.separator(this);
    }

    public Boolean getNotify_new_estate() {
        return notify_new_estate;
    }

    public Boolean getChange_price_notify() {
        return change_price_notify;
    }

    public void setNotify_new_estate(Boolean notify_new_estate) {
        this.notify_new_estate = notify_new_estate;
    }

    public void setChange_price_notify(Boolean change_price_notify) {
        this.change_price_notify = change_price_notify;
    }

    public static class Builder extends Utente.Builder<Acquirente.Builder>{

        private Boolean notify_new_estate = null;
        private Boolean change_price_notify = null;

        public Builder(int id_user, String email) {
            super(id_user, email);
        }

        public Builder setNotifyNewEstate(Boolean notify_new_estate){
            this.notify_new_estate=notify_new_estate;
            return this;
        }

        public Builder setChangePriceNotify(Boolean change_price_notify){
            this.change_price_notify = change_price_notify;
            return this;
        }

        public Acquirente build(){
            return new Acquirente(this);
        }



    }
}
