package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.EstateDAO;

import org.md.Estate.Estate;
import org.md.Utente.Agent;

import java.util.logging.Logger;

public class EstatePostgreDAO implements EstateDAO {

    private static final String TABLE = "immobile";

    private CommunicationWithPostgre connection;

    private static final Logger logger = Logger.getLogger(EstatePostgreDAO.class.getName());

    public EstatePostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public String createEstate(Estate newEstate) {

//        """
//            {
//            "idEstate":3,
//            "agente":{"idUser":1,"nome":"marcofradddd","email":"utente154545@email.com","cognome":"Paoli","password":"","notifyAppointment":true,"idPushNotify":"TOKEN","biografia":"Sono un figo","profilePic":"foto.png",
//            "agency":{"codicePartitaIVA":"11111111111","nome":"marcofradddd","sede":"via","email":null,"admins":null,"agents":null}
//            },
//            "indirizzo":{"idIndirizzo":1,"stato":"1","citta":"1","via":"1","numeroCivico":"1","cap":1},"agenzia":{"codicePartitaIVA":"11111111111","nome":"marcofradddd","sede":"via","email":null,"admins":null,"agents":null},"foto":"1","descrizione":"1","price":1.0,"space":1.0,"rooms":1,"floor":1,"wc":1,"garage":1,"elevator":true,"classeEnergetica":{"nome":"A","energeticClass":"A","energeticRangeClass":"Medium"},"mode":null,"stato":{"name":"New"}
//        """
//
//        """
//                INSERT INTO Immobile (
//                    idAgente,\s
//                    idIndirizzo,\s
//                    partitaiva,\s
//                    Foto,\s
//                    Descrizione,\s
//                    Prezzo,\s
//                    Dimensioni,\s
//                    Stanze,\s
//                    Piano,\s
//                    Bagni,\s
//                    Garage,\s
//                    Ascensore,\s
//                    ClasseEnergetica,\s
//                    Modalita,\s
//                    Stato
//                )
//                VALUES (
//                    1, -- idAgente (deve esistere nella tabella AgenteImmobiliare)
//                    1, -- idIndirizzo (deve esistere nella tabella Indirizzo)
//                    '11111111111', -- partitaiva (valore VARCHAR)
//                    'foto.jpg', -- Foto (valore TEXT)
//                    'Una bella casa in centro', -- Descrizione (valore TEXT)
//                    250000.00, -- Prezzo (valore DECIMAL)
//                    120, -- Dimensioni (valore INT)
//                    3, -- Stanze (valore INT)
//                    2, -- Piano (valore INT)
//                    2, -- Bagni (valore INT)
//                    1, -- Garage (valore INT)
//                    TRUE, -- Ascensore (valore BOOLEAN)
//                    'A+', -- ClasseEnergetica (valore VARCHAR)
//                    'Vendita', -- Modalita (valore VARCHAR)
//                    'Nuovo' -- Stato (valore Stato)
//                );
//                """

//        String query = "INSERT INTO "+TABLE+""" VALUES
//                """;

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