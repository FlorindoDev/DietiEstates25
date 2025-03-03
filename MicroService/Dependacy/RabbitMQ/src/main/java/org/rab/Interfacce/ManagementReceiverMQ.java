package org.rab.Interfacce;

import org.exc.DietiEstateException;

public interface ManagementReceiverMQ {
     void deQueue(String message) throws DietiEstateException;

}
