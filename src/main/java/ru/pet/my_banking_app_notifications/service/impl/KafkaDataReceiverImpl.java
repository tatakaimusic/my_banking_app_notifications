package ru.pet.my_banking_app_notifications.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import ru.pet.my_banking_app_notifications.service.KafkaDataReceiver;
import ru.pet.my_banking_app_notifications.service.KafkaDataService;

import java.io.IOException;

@Service
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final KafkaDataService kafkaDataService;

    @Autowired
    public KafkaDataReceiverImpl(
            KafkaReceiver<String, Object> receiver, KafkaDataService kafkaDataService
    ) {
        this.receiver = receiver;
        this.kafkaDataService = kafkaDataService;
    }

    @PostConstruct
    private void init(){
        fetch();
    }

    @Override
    public void fetch() {
        Gson gson = new GsonBuilder()
                .create();
        receiver.receive()
                .subscribe(r -> {
                    String email = gson.fromJson(r.value().toString(), String.class);
                    try {
                        kafkaDataService.handleEmail(email);
                    } catch (MessagingException | TemplateException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    r.receiverOffset().acknowledge();
                });
    }

}
