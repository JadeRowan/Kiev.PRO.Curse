package ua.kiev.prog.oauth2.loginviagoogle.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kiev.prog.oauth2.loginviagoogle.dto.AccountDTO;
import ua.kiev.prog.oauth2.loginviagoogle.services.NotificationService;


import javax.xml.bind.ValidationException;

@RestController
public class RegistrationController {

    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/signup-succes")
    public String signupSucces(){

        AccountDTO accountDTO = AccountDTO.of("tarnavskiy.mik@gmail.com", "Mik", null);

        try{
            notificationService.sendNotification(accountDTO);
        }catch (MailException e){
            logger.info("Error: "+ e.getMessage());

        }
        return "Thank you for registering with us.";
    }
}
