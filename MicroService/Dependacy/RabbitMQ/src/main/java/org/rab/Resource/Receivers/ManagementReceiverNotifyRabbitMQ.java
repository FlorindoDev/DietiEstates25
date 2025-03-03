package org.rab.Resource.Receivers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dao.Interfacce.NotifyDAO;
import org.dao.postgre.NotifyPostgreDAO;
import org.email.Interfacce.EmailSender;
import org.email.JakartaEmail.EmailSenderForNotify;
import org.exc.DietiEstateException;
import org.md.Notify.Notify;
import org.rab.Interfacce.ManagementReceiverMQ;
import org.rab.Resource.Configs.ConfigRabbitNotifyAppointmentMQ;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ManagementReceiverNotifyRabbitMQ implements ManagementReceiverMQ{
    public static final String THREAD_CONSUMER = "5";

    private static final Logger logger = Logger.getLogger(ManagementReceiverNotifyRabbitMQ.class.getName());
    @Override
    @RabbitListener(queues = ConfigRabbitNotifyAppointmentMQ.QUEUE_NAME, concurrency = THREAD_CONSUMER)
    public void deQueue(String message) throws DietiEstateException {

        NotifyDAO notifyDAO = new NotifyPostgreDAO();

        ObjectMapper objectMapper = new ObjectMapper();

        EmailSender senderEmail = new EmailSenderForNotify();

        Notify notification;

        try {

            notification = objectMapper.readValue(message, Notify.class);
            notifyDAO.createNotify(notification);

            String content = notification.getTipo() + ", appuntamento del " + notification.getData();

            senderEmail.setHtmlContent(content);
            senderEmail.sendEmail(notification.getAcquirente().getEmail());



        } catch (JsonProcessingException e) {
            logger.info("[!] Errore Deseralizzazione notifica: " + e.getMessage());
        }catch (Exception e){
            logger.info("[!] Errore durante invio notifica: " + e.getMessage());
        }


        logger.info("[!] il messaggio ricevuto: " + message);
    }
}
