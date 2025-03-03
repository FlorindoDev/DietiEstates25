package org.dao.postgre;

import DBLib.Postgres.CommunicationWithPostgre;
import org.dao.Interfacce.AcquirenteDAO;
import org.dao.Interfacce.NotifyDAO;
import org.exc.DataBaseException.ErrorExecutingQuery;
import org.exc.DietiEstateException;
import org.md.Notify.Notify;
import org.md.Utente.Acquirente;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.logging.Logger;

public class NotifyPostgreDAO implements NotifyDAO {

    CommunicationWithPostgre connection;

    private static final String ERROR_EXECUTING_QUERY = "[-] Error executing query: ";

    private static final Logger logger = Logger.getLogger(NotifyPostgreDAO.class.getName());

    public NotifyPostgreDAO() {
        this.connection = new CommunicationWithPostgre();
    }

    @Override
    public void createNotify(Notify notify) throws DietiEstateException {

        Acquirente acquirente = getAcquirente(notify);

        String query = "INSERT INTO Notifica(tiponotifica,messaggio,data,dataricezione,idacquirente,idimmobile) VALUES (?::notificatipo,?,?,?,?,?)";

        try{
            PreparedStatement stmt = connection.getStatment(query);

            stmt.setString(1,notify.getTipo());
            stmt.setString(2,notify.getMessage());
            stmt.setDate(3,Date.valueOf(notify.getData()));
            stmt.setDate(4, Date.valueOf(LocalDate.now()));
            stmt.setInt(5,acquirente.getIdUser());
            stmt.setInt(6,notify.getEstate().getIdEstate());

            connection.makeQueryUpdate(stmt);

        }catch (Exception e){
            logger.severe(ERROR_EXECUTING_QUERY + e.getMessage());
            throw new ErrorExecutingQuery();
        }

    }

    private Acquirente getAcquirente(Notify notify) throws DietiEstateException {

        AcquirenteDAO takeAcquirente = new AcquirentePostgreDAO();

        String emailAcquirente = notify.getAcquirente().getEmail();

        Acquirente acquirente = new Acquirente.Builder(0,emailAcquirente)
                .build();

        acquirente = takeAcquirente.getUser(acquirente);
        return acquirente;
    }
}
