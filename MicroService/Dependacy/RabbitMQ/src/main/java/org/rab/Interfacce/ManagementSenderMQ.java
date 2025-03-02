package org.rab.Interfacce;

import org.exc.DietiEstateException;

public interface ManagementSenderMQ {

    boolean enQueueNotify(String message) throws DietiEstateException;

}
