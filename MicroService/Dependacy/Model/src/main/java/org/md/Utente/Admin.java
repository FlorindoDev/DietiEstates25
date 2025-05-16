package org.md.Utente;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import org.md.Agency.Agency;

public class Admin extends Utente{

    @QueryParam("isSupport")
    private Boolean isSupport;

    @QueryParam("agency")
    private Agency agency;

    public Admin() {}

    protected Admin(Builder builder) {
        super(builder);
        this.isSupport = builder.isSupport;
        this.agency = builder.agency;
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
