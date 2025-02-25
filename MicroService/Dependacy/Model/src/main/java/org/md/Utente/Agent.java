package org.md.Utente;

import org.md.Agency.Agency;
import org.md.Utente.interfacce.UserSeparators;

public class Agent extends Utente{

    private String biografia = "";
    private String profilePic = "";
    private Agency agency;
    public Agent() {}

    public Agent(Builder builder) {
        super(builder);
        this.biografia = builder.biografia;
        this.profilePic = builder.profilePic;
        this.agency = builder.agency;
    }

    @Override
    public void Separator(UserSeparators split) {
        split.separator(this);
    }

    public String getBiografia() {
        return biografia;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public static class Builder extends Utente.Builder<Agent.Builder>{
        private String biografia;
        private String profilePic;

        private Agency agency;

        public Builder(int iduser, String email){
            super(iduser,email);
        }

        public Builder setBiografia(String biografia){
            this.biografia = biografia;
            return this;
        }

        public Builder setProfilePic(String profilePic){
            this.profilePic = profilePic;
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
