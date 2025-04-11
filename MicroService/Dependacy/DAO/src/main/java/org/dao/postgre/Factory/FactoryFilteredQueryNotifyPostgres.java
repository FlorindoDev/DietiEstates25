package org.dao.postgre.Factory;


import org.dao.Interfacce.Factory.QueryFactoryNotify;
import org.dao.Interfacce.Factory.QueryParametersNotify;

import java.util.Arrays;
import java.util.List;

public class FactoryFilteredQueryNotifyPostgres implements QueryFactoryNotify {

    @Override
    public String notifySelectQueryAllFilter(QueryParametersNotify paramters) {

        List<String> allowedColumns = Arrays.asList("*","idnotifica","tiponotifica","messaggio", "email", "data", "tipo", "dataricezione, idimmobile, idacquirente");

        StringBuilder query = setColumns(paramters, allowedColumns);

        query.append(" FROM notifica ");

        query.append("where idacquirente = ? ");

        query.append(" Order by dataricezione ");

        return query.toString();
    }


    @Override
    public String agentNotifySelectQueryAllFilter(QueryParametersNotify parameters) {

        StringBuilder query = new StringBuilder("SELECT * FROM notifica ");

        query.append(" where idimmobile = ? ");

        query.append(" and tiponotifica = 'Appuntamento Da Confermare' ");

        query.append(" Order by dataricezione ");

        if (parameters.isOrder()){
            query.append("DESC");
        }else{
            query.append("ASC");
        }

        return query.toString();
    }

    public String notifySelectQueryAllColumns(QueryParametersNotify parameters) {

        StringBuilder query = new StringBuilder("SELECT * FROM notifica ");

        query.append(" where idacquirente = ? ");

        query.append(" Order by dataricezione ");

        if (parameters.isOrder()){
            query.append("DESC");
        }else{
            query.append("ASC");
        }


        return query.toString();
    }

    private StringBuilder setColumns(QueryParametersNotify paramters, List<String> allowedColumns) {
        List<String> safeColumns = paramters.getColumns()
                .stream()
                .filter(allowedColumns::contains)
                .toList();

        StringBuilder query = new StringBuilder("SELECT ");

        for(String column : safeColumns){
            query.append(column).append(",");
        }
        query.deleteCharAt(query.lastIndexOf(","));
        return query;
    }


}
