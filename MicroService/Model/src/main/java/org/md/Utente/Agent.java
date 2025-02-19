package org.md.Utente;

import org.md.Utente.interfacce.UserSeparators;

public class Agent extends Utente{

    private String Biografia = "";
    private String ProfilePic = "";

    public Agent() {}

    public Agent(Builder builder) {
        super(builder);
        this.Biografia = builder.Biografia;
        this.ProfilePic = builder.ProfilePic;
    }

    @Override
    public void Separator(UserSeparators split) {
        split.separator(this);
    }

    public String getBiografia() {
        return Biografia;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setBiografia(String biografia) {
        Biografia = biografia;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public static class Builder extends Utente.Builder<Agent.Builder>{
        private String Biografia;
        private String ProfilePic;

        public Builder(int id_user, String email){
            super(id_user,email);
        }

        public Builder setBiografia(String Biografia){
            this.Biografia = Biografia;
            return this;
        }

        public Builder setProfilePic(String ProfilePic){
            this.ProfilePic = ProfilePic;
            return this;
        }

        public Agent build(){
            return new Agent(this);
        }
    }

}
