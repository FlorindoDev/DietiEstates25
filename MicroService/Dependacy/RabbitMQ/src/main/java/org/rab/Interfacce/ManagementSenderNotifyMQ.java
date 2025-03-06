package org.rab.Interfacce;

import org.exc.DietiEstateException;

public interface ManagementSenderNotifyMQ {

    void enQueueAppointmentNotify(String message) throws DietiEstateException;

    void enQueueEstateNotify(String message) throws DietiEstateException;
}
