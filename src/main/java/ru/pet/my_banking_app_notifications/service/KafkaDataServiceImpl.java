package ru.pet.my_banking_app_notifications.service;

import org.springframework.stereotype.Service;

@Service
public class KafkaDataServiceImpl implements KafkaDataService {

    public void handleEmail(String email) {
        System.out.println(email);
    }

}
