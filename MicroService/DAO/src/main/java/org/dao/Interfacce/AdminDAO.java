package org.dao.Interfacce;

import org.exc.DataBaseException.ErrorCreateStatment;
import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Utente.Admin;
import org.md.Utente.Agent;

public interface AdminDAO {

    public Admin getUser(Admin admin) throws DietiEstateException;

    public void createUser(Admin admin) throws DietiEstateException;

    public void updateUser(Admin changes) throws DietiEstateException;

    public void removeAdmin(Admin admin) throws DietiEstateException;

    public void upgradeSupportAdmin(Admin admin) throws DietiEstateException;
    boolean isUserAbsent(Admin acquirente) throws DietiEstateException;
    boolean isUserPresent(Admin acquirente) throws DietiEstateException;

    public void downgradeSupport(Admin admin) throws DietiEstateException;
}
