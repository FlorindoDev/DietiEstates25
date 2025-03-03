package org.rab.Interfacce;

import org.exc.DietiEstateException;

public interface ManagementReceiverMQ {
     String deQueue(String message) throws DietiEstateException;

}
