package ua.kiev.prog.oauth2.loginviagoogle.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static String authorizationRequestBaseUri = "oauth2/authorization";

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/oauth_login")
    public String getLoginPage(Model model) {
        ClientRegistration googleClientRegistration = clientRegistrationRepository.findByRegistrationId("google");
        ClientRegistration facebookClientRegistration = clientRegistrationRepository.findByRegistrationId("facebook");
        ClientRegistration githubClientRegistration = clientRegistrationRepository.findByRegistrationId("github");

        model.addAttribute("google_name", googleClientRegistration.getClientName());
        model.addAttribute("google_url", authorizationRequestBaseUri + "/" + googleClientRegistration.getRegistrationId());
        model.addAttribute("facebook_name", facebookClientRegistration.getClientName());
        model.addAttribute("facebook_url", authorizationRequestBaseUri + "/" + facebookClientRegistration.getRegistrationId());
        model.addAttribute("github_name", githubClientRegistration.getClientName());
        model.addAttribute("github_url", authorizationRequestBaseUri + "/" + githubClientRegistration.getRegistrationId());
        return "oauth_login";
    }
}
