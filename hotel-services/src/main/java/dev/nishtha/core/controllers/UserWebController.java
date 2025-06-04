package dev.nishtha.core.controllers;

import dev.nishtha.core.dtos.UserRegisterDTO;
import dev.nishtha.core.dtos.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class UserWebController {

    private static final String PUBLIC_URL = "/public/web/users";
    private static final String SECURED_URL = "/secured/web/users";

    private final UserEntityController userEntityController;

    @GetMapping(PUBLIC_URL + "/register/customer")
    public String serveRegisterCustomerPage(Model model) {
        model.addAttribute("title", "Register Customer - WEB");
        model.addAttribute("userDto", UserRegisterDTO.builder().build());
        model.addAttribute("userType", "customer");
        if (!model.containsAttribute("success")) {
            model.addAttribute("success", false);
            model.addAttribute("userId", null);
        }
        return "users/register";
    }

    @GetMapping(SECURED_URL + "/register/admin")
    public String serveRegisterAdminPage(Model model) {
        model.addAttribute("title", "Register Admin - WEB");
        model.addAttribute("userDto", UserRegisterDTO.builder().build());
        model.addAttribute("userType", "admin");
        if (!model.containsAttribute("success")) {
            model.addAttribute("success", false);
            model.addAttribute("userId", null);
        }
        return "users/register";
    }

    @PostMapping(PUBLIC_URL + "/register")
    public String processCustomerRegistration(@ModelAttribute("userDto") UserRegisterDTO userRegisterDTO,
                                              RedirectAttributes rAtt) {
        ResponseEntity<UserResponseDTO> response = userEntityController.registerCustomer(userRegisterDTO);
        if (response != null && response.getStatusCode() == HttpStatus.CREATED) {
            rAtt.addFlashAttribute("success", true);
            UserResponseDTO savedCustomer = response.getBody();
            if (savedCustomer != null) rAtt.addFlashAttribute("userId", savedCustomer.getUsername());
        }
        return "redirect:/public/web/users/register/customer";
    }

    @PostMapping(SECURED_URL + "/register")
    public String processAdminRegistration(@ModelAttribute("userDto") UserRegisterDTO userRegisterDTO,
                                             RedirectAttributes rAtt) {
        ResponseEntity<UserResponseDTO> response = userEntityController.registerAdmin(userRegisterDTO);
        if (response != null && response.getStatusCode() == HttpStatus.CREATED) {
            rAtt.addFlashAttribute("success", true);
            UserResponseDTO savedAdmin = response.getBody();
            if (savedAdmin != null) rAtt.addFlashAttribute("userId", savedAdmin.getUsername());
        }
        return "redirect:/secured/web/users/register/admin";
    }

    @GetMapping(SECURED_URL + "/details/{id}")
    public String userDetails(Model model,
                              @PathVariable("id") String id) {
        model.addAttribute("title", "User Details - WEB");
        model.addAttribute("user", userEntityController.getUserByUsername(id).getBody());
        return "users/user";
    }

}
