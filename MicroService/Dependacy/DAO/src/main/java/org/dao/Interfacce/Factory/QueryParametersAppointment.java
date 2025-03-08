package org.dao.Interfacce.Factory;

import java.util.List;

public interface QueryParametersAppointment extends QueryParamters{

    String getEmail();
    void setEmail(String email);
    boolean isOrder();
    void setOrder(boolean order);

    List<String> getEsiti();

     void setEsiti(String tipoAppointment);

}
