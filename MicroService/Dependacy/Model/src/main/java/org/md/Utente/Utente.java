package org.md.Utente;

import org.exc.DietiEstateException;
import org.md.Serializzazione.Translate;
import org.md.Utente.interfacce.UserSeparators;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") // Specifica il tipo nel JSON
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Agent.class, name = "Agent"), // Registra "agent" come sottotipo
//        @JsonSubTypes.Type(value = Admin.class, name = "Admin"), // Registra "Admin" come sottotipo
//        @JsonSubTypes.Type(value = Acquirente.class, name = "Acquirente") // Registra "Admin" come sottotipo
//})
public class Utente extends Translate {

    protected int idUser;
    protected String nome;

    protected String email;
    protected String cognome;
    protected String password;
    protected Boolean notifyAppointment;
    protected String idPushNotify;

    public Utente() {}

    protected Utente(Builder builder){
        this.idUser = builder.idUser;
        this.nome = builder.nome;
        this.email = builder.email;
        this.cognome = builder.cognome;
        this.password = builder.password;
        this.notifyAppointment = builder.notifyAppointment;
        this.idPushNotify = builder.idPushNotify;


    }


    public void Separator(UserSeparators split) throws DietiEstateException {
        split.separator(this);
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCognome() {
        return cognome;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getNotifyAppointment() {
        return notifyAppointment;
    }

    public String getIdPushNotify() {
        return idPushNotify;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNotifyAppointment(Boolean notifyAppointment) {
        this.notifyAppointment = notifyAppointment;
    }

    public void setIdPushNotify(String idPushNotify) {
        this.idPushNotify = idPushNotify;
    }

    public static class Builder<typeBuilder extends Builder<typeBuilder>>{

        protected int idUser;
        protected String nome = "";

        protected String email = "";
        protected String cognome = "";
        protected String password = "";
        protected Boolean notifyAppointment = null;
        protected String idPushNotify = "";


        public Builder(int idUser, String email){
            this.idUser = idUser;
            this.email = email;
        }

        public typeBuilder setName(String nome){
            this.nome = nome;
            return self();
        }

        public typeBuilder setCognome(String cognome){
            this.cognome = cognome;
            return self();
        }

        public typeBuilder setPassword(String password){
            this.password = password;
            return self();
        }

        public typeBuilder setIdPushNotify(String idPushNotify){
            this.idPushNotify = idPushNotify;
            return self();
        }

        public typeBuilder setNotifyAppointment(Boolean notifyAppointment){
            this.notifyAppointment = notifyAppointment;
            return self();
        }

        public Utente build(){
            return new Utente(this);
        }

        protected typeBuilder self(){
            return (typeBuilder)this;
        }




    }


}
