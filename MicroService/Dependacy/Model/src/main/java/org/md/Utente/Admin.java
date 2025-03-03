package org.md.Utente;

import org.md.Agency.Agency;
import org.md.Utente.interfacce.UserSeparators;

public class Admin extends Utente{
    private Boolean isSupport;
    private Agency agency;

    public Admin() {}

    protected Admin(Builder builder) {
        super(builder);
        this.isSupport = builder.isSupport;
        this.agency = builder.agency;
    }

    @Override
    public void Separator(UserSeparators split) {
        split.separator(this);
    }

    public void setSupport(Boolean support) {
        isSupport = support;
    }

    public Boolean getSupport() {
        return isSupport;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }


    public static class Builder extends Utente.Builder<Admin.Builder>{
        private Boolean isSupport;
        private Agency agency;

        public Builder(int iduser, String email) {
            super(iduser, email);
        }

        public Builder setIsSupport(Boolean isSupport){
            this.isSupport=isSupport;
            return this;
        }

        public Builder setAgency(Agency agency){
            this.agency=agency;
            return this;
        }
        @Override
        public Admin build(){
            return new Admin(this);
        }


    }


}
