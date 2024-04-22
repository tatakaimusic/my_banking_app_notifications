package ru.pet.my_banking_app_notifications.config;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BeanConfig {

    @Bean
    public XML consumerXml() throws IOException {
        return new XMLDocument(
                getClass().getResourceAsStream("/kafka/consumer.xml").readAllBytes()
        );
    }

}
