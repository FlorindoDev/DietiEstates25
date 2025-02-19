package org.md.Utente;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.md.Serializzazione.TranslationToJson;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") // Specifica il tipo nel JSON
@JsonSubTypes({
        @JsonSubTypes.Type(value = Agent.class, name = "Agent"), // Registra "agent" come sottotipo
        @JsonSubTypes.Type(value = Admin.class, name = "Admin") // Registra "Admin" come sottotipo
})
public class Utente extends TranslationToJson {

    protected int id_user;
    protected String nome;
    protected String email;
    protected String cognome;
    protected String password;
    protected Boolean notify_appointment;
    protected String idPushNotify;
    private Boolean notify_new_estate;
    private Boolean change_price_notify;

    public Utente() {}

    public Utente(Builder builder){
        this.id_user = builder.id_user;
        this.nome = builder.nome;
        this.email = builder.email;
        this.cognome = builder.cognome;
        this.password = builder.password;
        this.notify_appointment = builder.notify_appointment;
        this.idPushNotify = builder.idPushNotify;
        this.notify_new_estate = builder.notify_new_estate;
        this.change_price_notify = builder.change_price_notify;


    }

    public int getId_user() {
        return id_user;
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

    public Boolean getNotify_appointment() {
        return notify_appointment;
    }

    public String getIdPushNotify() {
        return idPushNotify;
    }

    public Boolean getNotify_new_estate() {
        return notify_new_estate;
    }

    public Boolean getChange_price_notify() {
        return change_price_notify;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public void setNotify_appointment(Boolean notify_appointment) {
        this.notify_appointment = notify_appointment;
    }

    public void setIdPushNotify(String idPushNotify) {
        this.idPushNotify = idPushNotify;
    }

    public void setNotify_new_estate(Boolean notify_new_estate) {
        this.notify_new_estate = notify_new_estate;
    }

    public void setChange_price_notify(Boolean change_price_notify) {
        this.change_price_notify = change_price_notify;
    }

    public static class Builder<typeBuilder extends Builder<typeBuilder>>{

        protected int id_user;
        protected String nome = "";
        protected String email;
        protected String cognome = "";
        protected String password = "";
        protected Boolean notify_appointment = null;
        protected String idPushNotify = "";
        private Boolean notify_new_estate = null;
        private Boolean change_price_notify = null;


        public Builder(int id_user, String email){
            this.id_user = id_user;
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

        public typeBuilder setNotifyAppointment(Boolean notify_appointment){
            this.notify_appointment = notify_appointment;
            return self();
        }

        public typeBuilder setNotifyNewEstate(Boolean notify_new_estate){
            this.notify_new_estate=notify_new_estate;
            return self();
        }

        public typeBuilder setChangePriceNotify(Boolean change_price_notify){
            this.change_price_notify = change_price_notify;
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
