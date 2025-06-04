package dev.nishtha.core.contracts;

import dev.nishtha.core.dtos.UserLoginDTO;
import dev.nishtha.core.dtos.UserRegisterDTO;
import dev.nishtha.core.dtos.UserResponseDTO;
import dev.nishtha.core.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    UserResponseDTO registerCustomer(UserRegisterDTO userRegisterDTO);
    UserResponseDTO registerAdmin(UserRegisterDTO userRegisterDTO);
    List<UserResponseDTO> loadAllUsers();
    UserResponseDTO findUserByUsername(String username) throws UsernameNotFoundException;

}
