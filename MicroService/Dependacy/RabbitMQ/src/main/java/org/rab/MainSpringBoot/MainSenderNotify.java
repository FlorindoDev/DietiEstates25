package org.rab.MainSpringBoot;


import org.rab.Resource.Configs.ConfigRabbitNotifyAppointmentMQ;
import org.rab.Resource.Configs.ConfigRabbitNotifyEstateMQ;
import org.rab.Resource.Senders.ManagementSenderNotifyRabbitMQ;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


@SpringBootApplication
@ComponentScan(basePackages = {"org.rab.Resource.Senders", "org.rab.Resource.Configs"},
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ManagementSenderNotifyRabbitMQ.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ConfigRabbitNotifyAppointmentMQ.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ConfigRabbitNotifyEstateMQ.class)
        }
)
public class MainSenderNotify {

    public static void main(String[] args) {
        //Avvio di FrameWork Spring
    }
}