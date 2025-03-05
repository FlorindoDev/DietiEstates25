package org.rab.Resource.Configs;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigRabbitNotifyAppointmentMQ {

    public static final String QUEUE_NAME = "notify.queue";
    public static final String EXCHANGE_QUEUE_NAME = "notify.exchange";
    public static final String ROUTING_QUEUE_NAME = "notify.routing.key";

    @PostConstruct
    public void init() {
        // Questa configurazione viene eseguita solo una volta all'avvio dell'applicazione
    }

    @Bean
    protected Queue queue() {
        return new Queue(QUEUE_NAME, true);  // true = la coda è persistente
    }

    @Bean
    protected DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_QUEUE_NAME);
    }

    @Bean
    protected Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_QUEUE_NAME);
    }
}
