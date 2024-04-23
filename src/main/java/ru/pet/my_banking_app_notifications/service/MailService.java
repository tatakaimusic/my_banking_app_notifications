package ru.pet.my_banking_app_notifications.service;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface MailService {

    void sendEmail(String email) throws MessagingException, IOException, TemplateException;

}
