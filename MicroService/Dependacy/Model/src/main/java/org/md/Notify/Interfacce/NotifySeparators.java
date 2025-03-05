package org.md.Notify.Interfacce;


import org.exc.DietiEstateException;
import org.md.Notify.AppuntamentoAccettato;
import org.md.Notify.AppuntamentoDaConfermare;
import org.md.Notify.AppuntamentoRifiutato;
import org.md.Notify.Notify;

public interface NotifySeparators {
    void separator(Notify notify) throws DietiEstateException;
    void separator(AppuntamentoAccettato acceptedNotify) throws DietiEstateException;
    void separator(AppuntamentoDaConfermare pendingNotify) throws DietiEstateException;
    void separator(AppuntamentoRifiutato rejectedNotify) throws DietiEstateException;
}
