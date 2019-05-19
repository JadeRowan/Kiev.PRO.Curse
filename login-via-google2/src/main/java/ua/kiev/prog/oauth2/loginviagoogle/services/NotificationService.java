package ua.kiev.prog.oauth2.loginviagoogle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.kiev.prog.oauth2.loginviagoogle.dto.AccountDTO;


@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public  void sendNotification(AccountDTO account) throws MailException {
        SimpleMailMessage mail =new SimpleMailMessage();
        mail.setTo(account.getEmail());
        mail.setFrom("rougesoul.m@gmail.com");
        mail.setSubject("Some Subject");
        mail.setText("Some text,Some text,Some text,Some text,Some text,Some text,Some text");

        javaMailSender.send(mail);
    }
}
