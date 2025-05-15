package org.md.Utente;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.md.Agency.Agency;

public class Agent extends Utente{

    private String biografia = "";
    private String immagineprofilo = "";
    private Agency agency;
    public Agent() {}

    protected Agent(Builder builder) {
        super(builder);
        this.biografia = builder.biografia;
        this.immagineprofilo = builder.immagineprofilo;
        this.agency = builder.agency;
    }

    public String getBiografia() {
        return biografia;
    }

    @JsonProperty("immagineprofilo")
    public String getProfilePic() {
        return immagineprofilo;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    @JsonProperty("immagineprofilo")
    public void setProfilePic(String profilePic) {
        this.immagineprofilo = profilePic;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public static class Builder extends Utente.Builder<Agent.Builder>{
        private String biografia;
        private String immagineprofilo;

        private Agency agency;

        public Builder(int iduser, String email){
            super(iduser,email);
        }

        public Builder setBiografia(String biografia){
            this.biografia = biografia;
            return this;
        }

        public Builder setProfilePic(String immagineprofilo){
            this.immagineprofilo = immagineprofilo;
            return this;
        }

        public Agent.Builder setAgency(Agency agency){
            this.agency=agency;
            return this;
        }

        @Override
        public Agent build(){
            return new Agent(this);
        }
    }

}
