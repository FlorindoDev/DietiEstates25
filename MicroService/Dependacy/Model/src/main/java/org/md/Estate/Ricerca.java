package org.md.Estate;

import jakarta.ws.rs.QueryParam;
import org.md.Serializzazione.Translate;

public class Ricerca extends Translate {

    @QueryParam("idricerca")
    int idricerca;

    @QueryParam("comando")
    String comando;

    @QueryParam("idacquirente")
    int idacquirente;

    public Ricerca(int idricerca, String comando, int idacquirente) {
        this.idricerca = idricerca;
        this.comando = comando;
        this.idacquirente = idacquirente;
    }


    public int getIdricerca() {
        return idricerca;
    }

    public void setIdricerca(int idricerca) {
        this.idricerca = idricerca;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public int getIdacquirente() {
        return idacquirente;
    }

    public void setIdacquirente(int idacquirente) {
        this.idacquirente = idacquirente;
    }
}
