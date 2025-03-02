package org.rab.Resource.Receivers;

import org.exc.DietiEstateException;
import org.rab.Interfacce.ManagementReceiverMQ;
import org.rab.Resource.Configs.ConfigRabbitNotifyMQ;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ManagementReceiverRabbitMQ implements ManagementReceiverMQ{
    public static final String THREAD_CONSUMER = "5";

    private static final Logger logger = Logger.getLogger(ManagementReceiverRabbitMQ.class.getName());
    @Override
    @RabbitListener(queues = ConfigRabbitNotifyMQ.QUEUE_NAME, concurrency = THREAD_CONSUMER)
    public String deQueueNotify(String message) throws DietiEstateException {
        logger.info("[!] il messaggio: " + message);
        return null;
    }
}
