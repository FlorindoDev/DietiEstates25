package org.rab.Resource.Senders;

import org.exc.DietiEstateException;
import org.exc.DietiEstateMicroServiceException.FailToInsertOnQueue;
import org.rab.Interfacce.ManagementSenderMQ;
import org.rab.Resource.Configs.ConfigRabbitNotifyAppointmentMQ;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.logging.Logger;

@Service
public class ManagementSenderNotifyRabbitMQ implements ManagementSenderMQ {

    private final RabbitTemplate rabbitTemplate;

    private static final Logger logger = Logger.getLogger(ManagementSenderNotifyRabbitMQ.class.getName());
    @Autowired
    public ManagementSenderNotifyRabbitMQ(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public boolean enQueue(String message) throws DietiEstateException {
        logger.info("[!] il messaggio inviato: " + message);
        try{
            rabbitTemplate.convertAndSend(ConfigRabbitNotifyAppointmentMQ.EXCHANGE_QUEUE_NAME, ConfigRabbitNotifyAppointmentMQ.ROUTING_QUEUE_NAME, message);
        }catch (Exception e){
            logger.info("[!] fallimento inserimento in Queue: " + message);
            throw  new FailToInsertOnQueue(e.getMessage());
        }

        return true;
    }


}
