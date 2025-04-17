package org.dao.Interfacce.Factory;

public interface QueryFactoryAppointment {
    String selectQueryAcquirenteAllColumns(QueryParametersAppointment parameters);

    String selectSpecificAppointment(QueryParametersAppointment parameters);

    String selectQueryAgentAllColumns(QueryParametersAppointment parameters);
}
