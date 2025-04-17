package org.dao.Interfacce.Factory;

import java.util.List;

public interface QueryParametersAppointment extends QueryParamters{

    String getEmail();
    void setEmail(String email);
    boolean isOrder();
    void setOrder(boolean order);

    List<String> getEsiti();

     void setEsiti(String tipoAppointment);

    int getIdAppointment();

    void setIdAppointment(int id);

    String getEsitiAppointment();

    void setEsitiAppointment(String esito);

    boolean isExtended();

    void setExtended(boolean extended);

    boolean isAgent();

    void setAgent(boolean agent);
}
