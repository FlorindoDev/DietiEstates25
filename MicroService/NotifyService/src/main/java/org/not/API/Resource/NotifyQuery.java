package org.not.API.Resource;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import org.dao.Interfacce.Factory.QueryParametersNotify;

import java.util.Arrays;
import java.util.List;

public class NotifyQuery implements QueryParametersNotify {

    @QueryParam("email")
    private String email;

    @QueryParam("orderbydate")
    @DefaultValue("false")
    private boolean order;

    @QueryParam("columns")
    @DefaultValue("*")
    private String columnsParam;


    @Override
    public List<String> getColumns() {
        return Arrays.asList(columnsParam.split(","));
    }

    @Override
    public void setColumns(String columnsParam) {
        this.columnsParam = columnsParam;
    }

    @Override
    // Getters e Setters
    public String getEmail() {
        return email;
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
