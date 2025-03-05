package org.rab.Resource.NotifySenders;

import org.exc.DietiEstateException;
import org.md.Notify.AppuntamentoAccettato;
import org.md.Notify.AppuntamentoDaConfermare;
import org.md.Notify.AppuntamentoRifiutato;
import org.md.Notify.Interfacce.NotifySeparators;
import org.md.Notify.Notify;

public class NotifySpliter implements NotifySeparators {

    NotifySenders notifySenders = new NotifySenders();

    @Override
    public void separator(Notify notify) throws DietiEstateException {
        notifySenders.sendNotify(notify);
    }

    @Override
    public void separator(AppuntamentoAccettato acceptedNotify) throws DietiEstateException {
        notifySenders.sendNotify(acceptedNotify);
    }

    @Override
    public void separator(AppuntamentoDaConfermare pendingNotify) throws DietiEstateException {
        notifySenders.sendNotify(pendingNotify);
    }

    @Override
    public void separator(AppuntamentoRifiutato rejectedNotify) throws DietiEstateException {
        notifySenders.sendNotify(rejectedNotify);
    }
}
