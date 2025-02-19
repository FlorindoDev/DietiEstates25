package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.exc.ErrorExecutingQuery;
import org.md.Utente.Acquirente;

import java.util.logging.Logger;

public class AcquirentePostgreDAO extends UtentePostgreDAO implements AcquirenteDAO {
    private CommunicationWithPostgre connection = new CommunicationWithPostgre();
    private static final Logger logger = Logger.getLogger(CommunicationWithPostgre.class.getName());

    public AcquirentePostgreDAO() {}

    @Override
    public Acquirente getUser(Acquirente acquirente) throws ErrorExecutingQuery {

        String Query="SELECT * FROM acquirente where ? = email";

        PrepareStatmentAndContactDB(acquirente, Query);

        acquirente = new Acquirente.Builder(connection.extractInt("idagente"), connection.extractString("email"))
                .setName(connection.extractString("nome"))
                .setCognome(connection.extractString("cognome"))
                .setPassword(connection.extractString("password"))
                .build();

        return acquirente;
    }

    @Override
    public void createUser(Acquirente acquirente) {

    }

    @Override
    public void updateUser(Acquirente changes) {

    }
}
