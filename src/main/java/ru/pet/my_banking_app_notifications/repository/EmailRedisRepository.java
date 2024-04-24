package ru.pet.my_banking_app_notifications.repository;

public interface EmailRedisRepository {

    void save(String email, String code);

}
