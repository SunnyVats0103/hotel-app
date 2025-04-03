package dev.nishtha.controllers;

import dev.nishtha.contracts.UserService;
import dev.nishtha.dtos.UserDTO;
import dev.nishtha.entities.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final UserService userService;

    @PostMapping("/register")
    public Owner registerOwner(Owner owner) {
        return userService.registerOwner(owner);
    }

    @PostMapping("/login")
    public Owner loginOwner(UserDTO owner) {
        return userService.loginOwner(owner);
    }

}
