package org.dao.Interfacce.Factory;

public interface QueryParametersAppointment extends QueryParamters{

    String getEmail();
    void setEmail(String email);
    boolean isOrder();
    void setOrder(boolean order);

}
