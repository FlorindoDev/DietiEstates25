package org.md.Agency;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import org.md.Serializzazione.Translate;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

import java.util.ArrayList;


public class Agency extends Translate {

    @QueryParam("codicePartitaIVA")
    @DefaultValue("")
    protected String codicePartitaIVA;

    @QueryParam("nome")
    protected String nome;

    @QueryParam("sede")
    protected String sede;

    protected ArrayList<Admin> admins;

    protected ArrayList<Agent> agents;

    public Agency() {}

    public Agency(Builder builder) {
        this.codicePartitaIVA = builder.codicePartitaIVA;
        this.nome = builder.nome;
        this.sede = builder.sede;
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

    public String getCodicePartitaIVA() {
        return codicePartitaIVA;
    }

    public String getNome() {
        return nome;
    }

    public String getSede() {
        return sede;
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
