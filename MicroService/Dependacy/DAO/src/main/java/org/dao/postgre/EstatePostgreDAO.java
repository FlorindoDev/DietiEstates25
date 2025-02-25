package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.EstateDAO;

import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.json.JSONObject;
import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DataBaseException.EstateNotExists;
import org.exc.DataBaseException.UserNotExists;
import org.exc.DietiEstateException;
import org.md.Estate.Estate;
import org.md.Utente.Agent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class EstatePostgreDAO implements EstateDAO {

    private static final String TABLE = "immobile";
    public static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";

    private CommunicationWithPostgre connection;

    private static final Logger logger = Logger.getLogger(EstatePostgreDAO.class.getName());

    public EstatePostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public String createEstate(Estate newEstate) throws DietiEstateException {

//        """
//            {
//            "idEstate":3,
//            "agente":{"idUser":1,"nome":"marcofradddd","email":"utente154545@email.com","cognome":"Paoli","password":"","notifyAppointment":true,"idPushNotify":"TOKEN","biografia":"Sono un figo","profilePic":"foto.png",
//            "agency":{"codicePartitaIVA":"11111111111","nome":"marcofradddd","sede":"via","email":null,"admins":null,"agents":null}
//            },
//            "indirizzo":{"idIndirizzo":1,"stato":"1","citta":"1","via":"1","numeroCivico":"1","cap":1},"agenzia":{"codicePartitaIVA":"11111111111","nome":"marcofradddd","sede":"via","email":null,"admins":null,"agents":null},"foto":"1","descrizione":"1","price":1.0,"space":1.0,"rooms":1,"floor":1,"wc":1,"garage":1,"elevator":true,"classeEnergetica":{"nome":"A","energeticClass":"A","energeticRangeClass":"Medium"},"mode":null,"stato":{"name":"New"}
//        """

        StringBuilder query = new StringBuilder("INSERT INTO " + TABLE + " (");


        JSONObject jsonObject = new JSONObject(newEstate.TranslateToJson());
        if (!jsonObject.isEmpty()) {

            for (String key : jsonObject.keySet()) {
                query.append("'"+key+"'"+ ", ");
            }

            query.setLength(query.length() - 2);
            query.append(") VALUES (");

            for (String key : jsonObject.keySet()) {
                query.append("?, ");
//              query.append(jsonObject.get(key)+ ", ");
            }

            query.setLength(query.length() - 2);
            query.append(")");

        }

        System.out.println(query);

        PreparedStatement stmt = connection.getStatment(String.valueOf(query));
        try {
            int index = 1;
            for (String key : jsonObject.keySet()) {
                stmt.setObject(index++, jsonObject.get(key)); //parte da 1
            }

            System.out.println(query);

            connection.makeQueryUpdate(stmt);
        }catch (SQLException e){
            logger.severe("[-] Error executing query: " + e.getMessage());
            throw new ErrorExecutingQuery();
        }

        return "";
    }

    @Override
    public String changeEstate(Estate changEstate) {
        return "";
    }

    @Override
    public Agent getAgent(Estate agency) {
        return null;
    }

    @Override
    public void addEstateAgent(Estate estate, Agent agent) throws DietiEstateException {
        String query= "UPDATE immobile SET idagent = ? WHERE idimmobile = ? ";

        PreparedStatement stmt;
        try {
            stmt = connection.getStatment(query);
            stmt.setInt(1, agent.getIdUser());
            stmt.setInt(2, estate.getIdEstate());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        makeEstateUpdate(stmt);

    }


    private int makeEstateUpdate(PreparedStatement stmt) throws ErrorExecutingQuery {
        int res = 0;
        try {
            res = connection.makeQueryUpdate(stmt);
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
        return res;
    }

    @Override
    public boolean IsEstatePresent(Estate estate) throws DietiEstateException {
        String query="SELECT * FROM immobile where idimmobile = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.getStatment(query);

            stmt.setInt(1, estate.getIdEstate());
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorCreateStatment();
        }

        try {
            connection.makeQuery(stmt);
            if(!connection.hasNextRow()) throw new EstateNotExists();
            return true;
        } catch (SQLException e) {
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }
    }



    //    @Override
//    public ArrayList<Estate> getEstate(Agency agency) throws DietiEstateException {
//
//        String query = "SELECT * FROM Immobile where partitaiva = ?";
//
//        PreparedStatement stmt = connection.getStatment(query);
//
//        try {
//            stmt.setString(1, agency.getCodicePartitaIVA());
//        } catch (SQLException e){
//            logger.severe("Error executing query: " + e.getMessage());
//            throw new ErrorCreateStatment();
//        }
//
//        try {
//            connection.makeQuery(stmt);
//        }catch (SQLException e){
//            logger.severe("Error executing query: " + e.getMessage());
//            throw new ErrorExecutingQuery();
//        }
//
//        ArrayList<Estate> estateList= new ArrayList<Estate>();
//        try {
//            while(connection.hasNextRow()){
//                connection.nextRow();
//                System.out.println("idimmobile: " + connection.extractInt("idimmobile"));
//                System.out.println("idagente: " + connection.extractString("idagente"));
//                System.out.println("idindirizzo: " + connection.extractString("idindirizzo"));
//                System.out.println("partitiaiva: " + connection.extractString("partitiaiva"));
//                System.out.println("foto: " + connection.extractString("foto"));
//                System.out.println("descrizione: " + connection.extractString("descrizione"));
//                System.out.println("prezzo: " + connection.extractString("prezzo"));
//                System.out.println("dimensioni: " + connection.extractString("dimensioni"));
//                System.out.println("stanze: " + connection.extractString("stanze"));
//                System.out.println("piano: " + connection.extractString("piano"));
//                System.out.println("bagni: " + connection.extractString("bagni"));
//                System.out.println("garage: " + connection.extractString("garage"));
//                System.out.println("ascensore: " + connection.extractString("ascensore"));
//                System.out.println("classeenergetica: " + connection.extractString("classeenergetica"));
//                System.out.println("modalita: " + connection.extractString("modalita"));
//                System.out.println("stato: " + connection.extractString("stato"));
//                System.out.println(); // Per separare le righe
//
//                Estate estate = new Estate.Builder<>(connection.extractInt("idimmobile"))
//                        .setAgente(new Agent.Builder<>()
//                                connection.extractString("idagente"))
//                        .setIndirizzo(connection.extractString("idindirizzo"))
//                        .setPartitaIva(connection.extractString("partitiaiva"))
//                        .setFoto(connection.extractString("foto"))
//                        .setDescrizione(connection.extractString("descrizione"))
//                        .setPrezzo(connection.extractString("prezzo"))
//                        .setDimensioni(connection.extractString("dimensioni"))
//                        .setStanze(connection.extractString("stanze"))
//                        .setPiano(connection.extractString("piano"))
//                        .setBagni(connection.extractString("bagni"))
//                        .setGarage(connection.extractString("garage"))
//                        .setAscensore(connection.extractString("ascensore"))
//                        .setClasseEnergetica(connection.extractString("classeenergetica"))
//                        .setModalita(connection.extractString("modalita"))
//                        .setStato(connection.extractString("stato"))
//                        .build();
//
//                estateList.add(estate);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }

}