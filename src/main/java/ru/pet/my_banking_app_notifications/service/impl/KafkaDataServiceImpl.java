package ru.pet.my_banking_app_notifications.service.impl;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pet.my_banking_app_notifications.service.KafkaDataService;
import ru.pet.my_banking_app_notifications.service.MailService;

import java.io.IOException;

@Service
public class KafkaDataServiceImpl implements KafkaDataService {

    private final MailService mailService;

    @Autowired
    public KafkaDataServiceImpl(MailService mailService) {
        this.mailService = mailService;
    }

    public void handleEmail(String email) throws MessagingException, TemplateException, IOException {
        System.out.println(email);
        mailService.sendEmail(email);
    }

}
