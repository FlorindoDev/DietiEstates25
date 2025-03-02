package org.rab.MainSpringBoot;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"org.rab.Resource.Senders", "org.rab.Resource.Configs"})
public class MainSenderNotify {

    public static void main(String[] args) {
        //Avvio di FrameWork Spring
    }
}