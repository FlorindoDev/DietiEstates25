package org.ap.API.Resource;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import org.dao.Interfacce.Factory.QueryParametersAppointment;

import java.util.Arrays;
import java.util.List;

public class AppointmentQuery implements QueryParametersAppointment {


    @QueryParam("email")
    private String email;

    @QueryParam("orderbydate")
    @DefaultValue("false")
    private boolean order;

    @QueryParam("columns")
    @DefaultValue("*")
    String columns;

    // Getters e Setters
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
}
