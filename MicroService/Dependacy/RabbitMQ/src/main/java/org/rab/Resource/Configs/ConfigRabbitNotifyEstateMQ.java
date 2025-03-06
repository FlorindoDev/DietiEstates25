package org.rab.Resource.Configs;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigRabbitNotifyEstateMQ {
    public static final String QUEUE_NAME = "notify.estate.queue";
    public static final String EXCHANGE_QUEUE_NAME = "notify.estate.exchange";
    public static final String ROUTING_QUEUE_NAME = "notify.estate.routing.key";

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
