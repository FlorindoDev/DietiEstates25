package org.rab.Resource.Receivers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.exc.DietiEstateException;
import org.md.Notify.Interfacce.NotifySeparators;
import org.md.Notify.Notify;
import org.rab.Interfacce.ManagementReceiverNotifyMQ;
import org.rab.Resource.Configs.ConfigRabbitNotifyAppointmentMQ;
import org.rab.Resource.NotifySenders.NotifySpliter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ManagementReceiverNotifyRabbitMQ implements ManagementReceiverNotifyMQ {
    private static final String THREAD_CONSUMER = "5";

    private final NotifySeparators spliter = new NotifySpliter();

    private static final Logger logger = Logger.getLogger(ManagementReceiverNotifyRabbitMQ.class.getName());
    @Override
    @RabbitListener(queues = ConfigRabbitNotifyAppointmentMQ.QUEUE_NAME, concurrency = THREAD_CONSUMER)
    public void deQueueAppointmentNotify(String message){

        try {

            Notify notification;
            ObjectMapper objectMapper = new ObjectMapper();
            notification = objectMapper.readValue(message, Notify.class);

            //richiama la funzione send per uno specifico sottotipo
            notification.separator(spliter);

        } catch (JsonProcessingException e) {
            logger.info("[!] Errore Deseralizzazione notifica: " + e.getMessage());

        }catch (DietiEstateException e) {
            logger.info("[!] Errore durante invio notifica: " + e.getMessage());

        }


        logger.info("[!] il messaggio ricevuto: " + message);
    }

}
