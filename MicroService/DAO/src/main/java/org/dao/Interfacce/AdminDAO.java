package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Agency.Agency;
import org.md.Utente.Admin;

public interface AdminDAO {
    boolean isAdminAbsent(Admin admin) throws DietiEstateException;

    public Admin getUser(Admin admin) throws DietiEstateException;

    public void createUser(Admin admin) throws DietiEstateException;

    public void updateUser(Admin changes) throws DietiEstateException;

    public void removeAdmin(Admin admin) throws DietiEstateException;

    public void upgradeSupportAdmin(Admin admin) throws DietiEstateException;
}
