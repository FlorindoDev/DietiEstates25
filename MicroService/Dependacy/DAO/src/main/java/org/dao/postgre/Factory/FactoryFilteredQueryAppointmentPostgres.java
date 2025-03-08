package org.dao.postgre.Factory;

import org.dao.Interfacce.Factory.QueryFactoryAppointment;
import org.dao.Interfacce.Factory.QueryParametersAppointment;

import java.util.Arrays;
import java.util.List;


public class FactoryFilteredQueryAppointmentPostgres implements QueryFactoryAppointment {

    @Override
    public String selectQueryAcquirenteAllColumns(QueryParametersAppointment parameters) {

        List<String> allowedColumns = Arrays.asList("'Da decidere'","'Rifiutato'","'Accettato'");

        StringBuilder query = new StringBuilder("SELECT * FROM appuntamento ");

        query.append(" where idacquirente = ? ");

        query.append(" and esito in (");

        setColumns(query,parameters, allowedColumns);

        query.append(") ");

        query.append(" Order by datarichiesta ");

        return query.toString();
    }

    public String selectQueryAgentAllColumns(){

        StringBuilder query = new StringBuilder("SELECT email as email_acquirente,idappuntamento,esito,data,idacquirente,idimmobile \n" +
                "FROM \n" +
                "\t(SELECT idacquirente as tmp_idacquirente,idappuntamento,esito,data,idimmobile \n" +
                " \tFROM \n" +
                " \t\t(SELECT idimmobile as tmp_idimmobile \n" +
                "\t\t FROM immobile join agenteimmobiliare ON immobile.idagente = agenteimmobiliare.idagente \n");

        query.append("\t\t\twhere agenteimmobiliare.idagente = ? ");

        query.append(") as tmp join appuntamento \n" +
        "\t ON tmp.tmp_idimmobile = appuntamento.idimmobile) as tmp join acquirente\n" +
                "ON tmp.tmp_idacquirente = acquirente.idacquirente");

        query.append(" Order by datarichiesta ");

        return query.toString();

    }

    private StringBuilder setColumns(StringBuilder query, QueryParametersAppointment parameters, List<String> allowedColumns) {
        List<String> safeColumns = parameters.getEsiti()
                .stream()
                .filter(allowedColumns::contains)
                .toList();

        for(String column : safeColumns){
            query.append(column).append(",");
        }
        query.deleteCharAt(query.lastIndexOf(","));
        return query;
    }
}
