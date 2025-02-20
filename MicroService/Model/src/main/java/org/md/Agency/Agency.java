package org.md.Agency;

import org.md.Serializzazione.TranslationToJson;


public class Agency extends TranslationToJson {
    String codice_partitaIVA;
    String nome;
    String sede;
    String email;

    public Agency() {}

    public Agency(Builder builder) {
        this.codice_partitaIVA = builder.codice_partitaIVA;
        this.nome = builder.nome;
        this.sede = builder.sede;
        this.email = builder.email;
    }

    public void setCodice_partitaIVA(String codice_partitaIVA) {
        this.codice_partitaIVA = codice_partitaIVA;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodice_partitaIVA() {
        return codice_partitaIVA;
    }

    public String getNome() {
        return nome;
    }

    public String getSede() {
        return sede;
    }

    public String getEmail() {
        return email;
    }

    public static class Builder<typeBuilder extends Agency.Builder<typeBuilder>>{

        String codice_partitaIVA;
        String nome = "";
        String sede = "";
        String email = "";


        public Builder(String codice_partitaIVA) {
            this.codice_partitaIVA = codice_partitaIVA;
        }

        public typeBuilder setNome(String name){
            this.nome = name;
            return self();
        }

        public typeBuilder setSede(String sede){
            this.sede = sede;
            return self();
        }
        public typeBuilder setEmail(String email){
            this.email = email;
            return self();
        }

        public Agency build(){
            return new Agency(this);
        }

        protected typeBuilder self(){
            return (typeBuilder)this;
        }




    }
}
