package org.ap.API.Resource;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import org.dao.Interfacce.Factory.QueryParametersAppointment;

import java.util.Arrays;
import java.util.List;

public class AppointmentQuery implements QueryParametersAppointment {


    @QueryParam("email")
    private String email;

    @QueryParam("id")
    @DefaultValue("0")
    private int idAppointment;

    @QueryParam("agent")
    @DefaultValue("true")
    private boolean agent;

    @QueryParam("extended")
    @DefaultValue("false")
    private boolean extended;


    @QueryParam("orderbydate")
    @DefaultValue("false")
    private boolean order;

    @QueryParam("columns")
    @DefaultValue("*")
    String columns;

    @QueryParam("esiti")
    @DefaultValue("'Da decidere','Rifiutato','Accettato'")
    String esitiAppointment;

    // Getters e Setters

    public List<String> getEsiti() {
        return Arrays.asList(esitiAppointment.split(","));
    }

    @Override
    public void setEsiti(String tipoAppointment) {
        this.esitiAppointment = tipoAppointment;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public List<String> getColumns() {
        return Arrays.asList(columns.split(","));
    }

    @Override
    public void setColumns(String columns) {
          this.columns = columns;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public boolean isOrder() {
        return order;
    }

    @Override
    public void setOrder(boolean order) {
        this.order = order;
    }

    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public String getEsitiAppointment() {
        return esitiAppointment;
    }

    public void setEsitiAppointment(String esitiAppointment) {
        this.esitiAppointment = esitiAppointment;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public boolean isAgent() {
        return agent;
    }

    public void setAgent(boolean agent) {
        this.agent = agent;
    }
}
