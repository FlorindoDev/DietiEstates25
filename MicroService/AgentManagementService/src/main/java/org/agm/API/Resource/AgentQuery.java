package org.agm.API.Resource;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class AgentQuery {

    @QueryParam("codicePartitaIVA")
    @DefaultValue("")
    private String codicePartitaIVA;

    @QueryParam("nome")
    private String nome;

    @QueryParam("sede")
    private String sede;

    public String getCodicePartitaIVA() {
        return codicePartitaIVA;
    }

    public void setCodicePartitaIVA(String codicePartitaIVA) {
        this.codicePartitaIVA = codicePartitaIVA;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
