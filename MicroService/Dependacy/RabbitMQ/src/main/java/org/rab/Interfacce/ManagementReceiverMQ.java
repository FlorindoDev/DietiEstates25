package org.rab.Interfacce;

import org.exc.DietiEstateException;

public interface ManagementReceiverMQ {
     String deQueueNotify(String message) throws DietiEstateException;

}
