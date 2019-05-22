package ua.kiev.prog.oauth2.loginviagoogle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.oauth2.loginviagoogle.dto.AccountDTO;
import ua.kiev.prog.oauth2.loginviagoogle.dto.PageCountDTO;
import ua.kiev.prog.oauth2.loginviagoogle.dto.ResultDTO;
import ua.kiev.prog.oauth2.loginviagoogle.dto.TaskDTO;
import ua.kiev.prog.oauth2.loginviagoogle.services.GeneralService;
import ua.kiev.prog.oauth2.loginviagoogle.services.MailSender;

import java.util.List;
import java.util.Map;

@RestController
public class MainController {

    private static final int PAGE_SIZE = 5;

    @Autowired
    private GeneralService generalService;

    @Autowired
    MailSender mailSender;

    @GetMapping("/account")
    public AccountDTO account(OAuth2AuthenticationToken auth) {
        Map<String, Object> attrs = auth.getPrincipal().getAttributes();

        String email = (String) attrs.get("email");
        String name = (String) attrs.get("name");
        String picture = AccountDTO.getPictureFrom(attrs);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(attrs); //Оставил для тестирования
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        return AccountDTO.of(email, name, picture);
    }

    @GetMapping("count")
    public PageCountDTO count(OAuth2AuthenticationToken auth) {
        String email = (String) auth.getPrincipal().getAttributes().get("email");
        return PageCountDTO.of(generalService.count(email), PAGE_SIZE);
    }

    @GetMapping("tasks")
    public List<TaskDTO> tasks(OAuth2AuthenticationToken auth,
                               @RequestParam(required = false, defaultValue = "0") int page) {
        String email = (String) auth.getPrincipal().getAttributes().get("email");
        return generalService.getTasks(email,
                PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, "id")
        );
    }

    @PostMapping("add")
    public ResponseEntity<ResultDTO> addTask(OAuth2AuthenticationToken auth,
                                             @RequestBody TaskDTO task) {
        String email = (String) auth.getPrincipal().getAttributes().get("email");
        generalService.addTask(email, task);

        return new ResponseEntity<>(new ResultDTO(), HttpStatus.OK);
    }

    @GetMapping("/send")
    public void send(){
        //Временная почта от temp-mail.org
        mailSender.send("ronezuxi@greentech5.com", "Some subject", "Hello!");
    }

}
