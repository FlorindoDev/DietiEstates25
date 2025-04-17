package org.dto;

import org.md.Serializzazione.Translate;

public class AppointmentSpecification extends Translate {

    private int idAppointment;
    private String data;
    private String esito;
    private String dataRichesta;
    private int idAcquirente;
    private int idEstate;
    private String nomeEcognome;
    private String viaEstate;

    public AppointmentSpecification(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getDataRichesta() {
        return dataRichesta;
    }

    public void setDataRichesta(String dataRichesta) {
        this.dataRichesta = dataRichesta;
    }

    public int getIdAcquirente() {
        return idAcquirente;
    }

    public void setIdAcquirente(int idAcquirente) {
        this.idAcquirente = idAcquirente;
    }

    public int getIdEstate() {
        return idEstate;
    }

    public void setIdEstate(int idEstate) {
        this.idEstate = idEstate;
    }

    public String getNomeEcognome() {
        return nomeEcognome;
    }

    public void setNomeEcognome(String nomeEcognome) {
        this.nomeEcognome = nomeEcognome;
    }

    public String getViaEstate() {
        return viaEstate;
    }

    public void setViaEstate(String viaEstate) {
        this.viaEstate = viaEstate;
    }
}
