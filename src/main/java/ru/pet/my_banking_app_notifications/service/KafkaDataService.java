package ru.pet.my_banking_app_notifications.service;

public interface KafkaDataService {

    void handleEmail(String email);

}
