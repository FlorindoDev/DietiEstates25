package org.rab.Interfacce;

import org.exc.DietiEstateException;

public interface ManagementSenderMQ {

    boolean enQueue(String message) throws DietiEstateException;

}
