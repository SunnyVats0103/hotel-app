package dev.sunny.hotelweb.controllers;

import dev.nishtha.dtos.UserDTO;
import dev.sunny.hotelweb.security.CustomAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@RequestMapping("/web")
public class HomeController {

    private final WebClient webClient;

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("title", "Home - Web");
        model.addAttribute("httpServletRequest", httpServletRequest);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("title", "Login - Web");
        model.addAttribute("httpServletRequest", httpServletRequest);
        model.addAttribute("userDto", UserDTO.builder().build());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute("userDto") UserDTO userDto, Model model) {
        try {
            Boolean isAuthenticated = webClient.post()
                    .uri("/api/owners/login")
                    .body(Mono.just(userDto), UserDTO.class)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (Boolean.TRUE.equals(isAuthenticated)) {
                Authentication auth = new CustomAuthenticationToken(userDto.getUserName());
                SecurityContextHolder.getContext().setAuthentication(auth);
                return "redirect:/web/home";
            } else {
                model.addAttribute("error", "Invalid username or password.");
                return "login";
            }
        } catch (WebClientResponseException e) {
            model.addAttribute("error", "Authentication service is unavailable.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/web/home";
    }
}