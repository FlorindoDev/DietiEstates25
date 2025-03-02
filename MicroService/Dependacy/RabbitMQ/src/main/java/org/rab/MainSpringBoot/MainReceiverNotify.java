package org.rab.MainSpringBoot;

import org.rab.Resource.Configs.ConfigRabbitNotifyMQ;
import org.rab.Resource.Receivers.ManagementReceiverNotifyRabbitMQ;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = {"org.rab.Resource.Receivers", "org.rab.Resource.Configs"},
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ManagementReceiverNotifyRabbitMQ.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ConfigRabbitNotifyMQ.class)
        }
)
public class MainReceiverNotify {
    public static void main(String[] args) {
        //Avvio di FrameWork Spring
    }
}
