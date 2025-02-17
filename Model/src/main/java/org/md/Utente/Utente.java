package org.md.Utente;

public class Utente {

    protected String id_user;
    protected String nome;
    protected String email;
    protected String cognome;
    protected String password;
    private Boolean notify_new_estate;
    private Boolean change_price_notify;
    private Boolean notify_appointment;

    public Utente(Builder builder){

    }

    public static class Builder{


    }


}
