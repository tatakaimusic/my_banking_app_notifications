package ru.pet.my_banking_app_notifications.service.impl;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.pet.my_banking_app_notifications.repository.EmailRedisRepository;
import ru.pet.my_banking_app_notifications.service.MailService;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    private final Configuration configuration;
    private final JavaMailSender javaMailSender;
    private final EmailRedisRepository emailRedisRepository;

    @Autowired
    public MailServiceImpl(
            Configuration configuration,
            JavaMailSender javaMailSender,
            EmailRedisRepository emailRedisRepository
    ) {
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
        this.emailRedisRepository = emailRedisRepository;
    }

    @Override
    public void sendEmail(String email) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Your confirmation code to register in my banking app");
        helper.setTo(email);
        String code = generateCode();
        String emailContent = getConfirmationEmailContent(code);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);

        emailRedisRepository.save(email, code);
    }

    private String getConfirmationEmailContent(String code) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("code", code);
        configuration.getTemplate("email_confirmation.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }

    private String generateCode() {
        Random rnd = new Random();
        char[] digits = new char[4];
        digits[0] = (char) (rnd.nextInt(9) + '1');
        for (int i = 1; i < digits.length; i++) {
            digits[i] = (char) (rnd.nextInt(10) + '0');
        }
        return new String(digits);
    }

}
