package org.md.Agency;

import org.md.Serializzazione.Translate;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

import java.util.ArrayList;


public class Agency extends Translate {
    protected String codicePartitaIVA;
    protected String nome;
    protected String sede;
    protected String email;

    protected ArrayList<Admin> admins;

    protected ArrayList<Agent> agents;

    public Agency() {}

    public Agency(Builder builder) {
        this.codicePartitaIVA = builder.codicePartitaIVA;
        this.nome = builder.nome;
        this.sede = builder.sede;
        this.email = builder.email;
        this.admins = builder.admins;
        this.agents = builder.agents;
    }

    public void setCodicePartitaIVA(String codicePartitaIVA) {
        this.codicePartitaIVA = codicePartitaIVA;
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

    public String getCodicePartitaIVA() {
        return codicePartitaIVA;
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

    public ArrayList<Admin> getAdmins() {
        return admins;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public void setAdmins(ArrayList<Admin> admins) {
        this.admins = admins;
    }

    public void setAgents(ArrayList<Agent> agents) {
        this.agents = agents;
    }

    public static class Builder<typeBuilder extends Agency.Builder<typeBuilder>>{

        String codicePartitaIVA;
        String nome = "";
        String sede = "";
        String email = "";
        ArrayList<Admin> admins = null;
        ArrayList<Agent> agents = null;


        public Builder(String codicePartitaIVA) {
            this.codicePartitaIVA = codicePartitaIVA;
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

        public typeBuilder setAdmins(ArrayList<Admin> admins){
            this.admins = admins;
            return self();
        }

        public typeBuilder setAgents(ArrayList<Agent> agents){
            this.agents = agents;
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
