package dev.nishtha.controllers;

import dev.nishtha.contracts.UserService;
import dev.nishtha.dtos.UserDTO;
import dev.nishtha.entities.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final UserService userService;

    @PostMapping("/register")
    public Customer registerCustomer(Customer customer) {
        return userService.registerCustomer(customer);
    }

    @PostMapping("/login")
    public Customer loginCustomer(UserDTO customer) {
        return userService.loginCustomer(customer);
    }

}
