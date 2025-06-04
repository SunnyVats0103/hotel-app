package dev.nishtha.core.controllers;

import dev.nishtha.core.contracts.UserService;
import dev.nishtha.core.dtos.UserRegisterDTO;
import dev.nishtha.core.dtos.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserEntityController {

    private final UserService userService;
    private static final String PUBLIC_URL = "/public/api/users";
    private static final String SECURED_URL = "/secured/api/users";

    @PostMapping(PUBLIC_URL + "/register")
    public ResponseEntity<UserResponseDTO> registerCustomer(UserRegisterDTO userRegisterDTO) {
        UserResponseDTO registeredCustomer = userService.registerCustomer(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredCustomer);
    }

    @PostMapping(SECURED_URL + "/register")
    public ResponseEntity<UserResponseDTO> registerAdmin(UserRegisterDTO userRegisterDTO) {
        UserResponseDTO registeredAdmin = userService.registerAdmin(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredAdmin);
    }

    @GetMapping(SECURED_URL + "/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.loadAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(SECURED_URL + "/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        UserResponseDTO user = userService.findUserByUsername(username);
        return ResponseEntity.ok(user);
    }

}
