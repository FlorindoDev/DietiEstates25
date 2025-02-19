package org.dao.Interfacce;

import org.exc.DietiEstateException;
import org.md.Utente.Admin;

public interface AdminDAO {
    public Admin getUser(Admin admin) throws DietiEstateException;

    public void createUser(Admin admin) throws DietiEstateException;

    public void updateUser(Admin changes) throws DietiEstateException;

}
