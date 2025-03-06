package org.dao.Interfacce;


import org.exc.DietiEstateException;
import org.md.Utente.Admin;

public interface AdminDAO {

    Admin getUser(Admin admin) throws DietiEstateException;

    void createUser(Admin admin) throws DietiEstateException;

    void updateUser(Admin changes) throws DietiEstateException;

    void removeAdmin(Admin admin) throws DietiEstateException;

    boolean isUserAbsentOverAll(Admin user) throws DietiEstateException;

    void upgradeSupportAdmin(Admin admin) throws DietiEstateException;

    boolean isUserAbsent(Admin acquirente) throws DietiEstateException;
    boolean isUserPresent(Admin acquirente) throws DietiEstateException;

    void downgradeSupport(Admin admin) throws DietiEstateException;

    void close();
}
