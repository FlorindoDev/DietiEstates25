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

        if (parameters.isOrder()){
            query.append("DESC");
        }else{
            query.append("ASC");
        }

        return query.toString();
    }

    public String selectSpecificAppointment(QueryParametersAppointment parameters) {

        StringBuilder query;



        if(parameters.isExtended()){

            if(!parameters.isAgent()){
                query = new StringBuilder("SELECT idappuntamento,data,datarichiesta,esito,nome,cognome,cap,numerocivico,via,citta " +
                        " FROM (((SELECT * FROM appuntamento where idappuntamento = ?) as appuntamento \n" +
                        "\t\t\t\t join immobile \n" +
                        "\t\t\t   ON immobile.idimmobile = appuntamento.idimmobile) as ap\n " +
                        "\t\t\t   JOIN indirizzo  on ap.idindirizzo = indirizzo.idindirizzo) as ap2\n " +
                        "\t\t\t   Join agenteimmobiliare on agenteimmobiliare.idagente = ap2.idagente");

            }else{
                query = new StringBuilder("SELECT idappuntamento,data,datarichiesta,esito,nome,cognome,cap,numerocivico,via,citta" +
                        " FROM (((SELECT * FROM appuntamento where idappuntamento = ?) as appuntamento \n" +
                        "        join immobile\n" +
                        "        ON immobile.idimmobile = appuntamento.idimmobile) as ap\n" +
                        "        JOIN indirizzo  on ap.idindirizzo = indirizzo.idindirizzo) as ap2\n" +
                        "        Join acquirente on acquirente.idacquirente = ap2.idacquirente");
            }
        }else{
            query = new StringBuilder("SELECT * FROM appuntamento where idappuntamento = ?");
        }



        return query.toString();
    }



    public String selectQueryAgentAllColumns(QueryParametersAppointment parameters){

        List<String> allowedColumns = Arrays.asList("'Da decidere'","'Rifiutato'","'Accettato'");

        StringBuilder query = new StringBuilder("SELECT email as email_acquirente,idappuntamento,esito,data,idacquirente,idimmobile,datarichiesta \n" +
                "FROM \n" +
                "\t(SELECT idacquirente as tmp_idacquirente,idappuntamento,esito,data,idimmobile,datarichiesta \n" +
                " \tFROM \n" +
                " \t\t(SELECT idimmobile as tmp_idimmobile \n" +
                "\t\t FROM immobile join agenteimmobiliare ON immobile.idagente = agenteimmobiliare.idagente \n");

        query.append("\t\t\twhere agenteimmobiliare.idagente = ? ");

        query.append(") as tmp join appuntamento \n" +
        "\t ON tmp.tmp_idimmobile = appuntamento.idimmobile) as tmp join acquirente\n" +
                "ON tmp.tmp_idacquirente = acquirente.idacquirente");

        query.append(" where esito in (");

        setColumns(query,parameters, allowedColumns);

        query.append(") ");

        query.append(" Order by datarichiesta ");

        if (parameters.isOrder()){
            query.append("DESC");
        }else{
            query.append("ASC");
        }


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
