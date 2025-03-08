package org.md.Notify;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Notify.Interfacce.NotifySeparators;
import org.md.Serializzazione.Translate;
import org.md.Utente.Acquirente;
import org.md.Utente.Utente;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type") // Specifica il tipo nel JSON
@JsonSubTypes({
        @JsonSubTypes.Type(value = AppuntamentoAccettato.class, name = "AppuntamentoAccettato"),
        @JsonSubTypes.Type(value = AppuntamentoRifiutato.class, name = "AppuntamentoRifiutato"),
        @JsonSubTypes.Type(value = AppuntamentoDaConfermare.class, name = "AppuntamentoDaConfermare")
})
public class Notify extends Translate {

    protected int idNotify;

    protected Estate estate;

    protected Acquirente acquirente;

    protected String data;
    protected String dataRicezione;

    protected String message;
    protected String tipo;  //Serve per i figli

    public Notify() {
    }

    protected Notify(Builder builder) {
        this.idNotify = builder.idNotify;
        this.estate = builder.estate;
        this.acquirente = builder.acquirente;
        this.data = builder.data;
        this.dataRicezione = builder.dataRicezione;
        this.message = builder.message;
        this.tipo = builder.tipo;
    }

    public void separator(NotifySeparators split) throws DietiEstateException {
        split.separator(this);
    }

    public int getIdNotify() {
        return idNotify;
    }


    public String getData() {
        return data;
    }

    public String getDataRicezione() {
        return dataRicezione;
    }

    public void setIdNotify(int idNotify) {
        this.idNotify = idNotify;
    }


    public void setData(String data) {
        this.data = data;
    }

    public void setDataRicezione(String dataRicezione) {
        this.dataRicezione = dataRicezione;
    }

    public String getMessage() {
        return message;
    }

    public String getTipo() {
        return tipo;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public Utente getAcquirente() {
        return acquirente;
    }

    public void setAcquirente(Acquirente acquirente) {
        this.acquirente = acquirente;
    }

    @Override
    public String TranslateToJson(){

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode jsonNode = objectMapper.valueToTree(this);

        jsonNode.put("type", this.getClass().getSimpleName());

        return jsonNode.toString();
    }

    public static class Builder<typeBuilder extends Builder<typeBuilder>>{
        protected int idNotify = 0;

        protected Estate estate;

        protected Acquirente acquirente;

        protected String data = "";

        protected String dataRicezione = "";

        protected String message = "";

        protected String tipo = "";


        public Builder(String message) {
            this.message = message;
        }

        public typeBuilder setIdNotify(int idNotify){
            this.idNotify = idNotify;
            return self();
        }

        public typeBuilder setEstate(Estate estate){
            this.estate = estate;
            return self();
        }

        public typeBuilder setTipo(String tipo){
            this.tipo = tipo;
            return self();
        }

        public typeBuilder setUser(Acquirente acquirente){
            this.acquirente = acquirente;
            return self();
        }

        public typeBuilder setData(String data){
            this.data = data;
            return self();
        }

        public typeBuilder setDataRicezione(String dataRicezione){
            this.dataRicezione = dataRicezione;
            return self();
        }

        public Notify build(){
            return new Notify(this);
        }

        protected typeBuilder self(){
            return (typeBuilder)this;
        }
    }
}
