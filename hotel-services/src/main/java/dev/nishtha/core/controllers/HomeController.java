package dev.nishtha.core.controllers;

import dev.nishtha.core.dtos.UserLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final String PUBLIC_URL = "/public/web";

    @GetMapping(PUBLIC_URL + "/home")
    public String home(Model model) {
        model.addAttribute("title", "Home - WEB");
        return "home";
    }

    @GetMapping(PUBLIC_URL + "/login")
    public String serveLoginPage(Model model) {
        model.addAttribute("title", "Login - WEB");
        model.addAttribute("userDto", UserLoginDTO.builder().build());

        return "login";
    }

    @GetMapping(PUBLIC_URL + "/logout")
    public String logout(Model model) {
        model.addAttribute("title", "Logout - WEB");
        return "redirect:/logout";
    }

}
