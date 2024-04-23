package ru.pet.my_banking_app_notifications.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
public class KafkaDataReceiverImpl implements KafkaDataReceiver{

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
                    kafkaDataService.handleEmail(email);
                    r.receiverOffset().acknowledge();
                });
    }

}
