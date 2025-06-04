package dev.nishtha.core.services;

import dev.nishtha.core.config.PasswordEncoderProvider;
import dev.nishtha.core.contracts.UserService;
import dev.nishtha.core.dtos.UserRegisterDTO;
import dev.nishtha.core.dtos.UserResponseDTO;
import dev.nishtha.core.entities.UserEntity;
import dev.nishtha.core.mappers.UserEntityMapper;
import dev.nishtha.core.repositories.UserEntityRepository;
import dev.nishtha.core.utils.SimpleDateFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userRepo;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoderProvider passwordEncoder;
    private final SimpleDateFormatUtil formatter;


    @Override
    public UserResponseDTO registerCustomer(UserRegisterDTO userRegisterDTO) {
        UserEntity rawUserEntity = userEntityMapper.toEntity(userRegisterDTO);
        rawUserEntity.setRole(Set.of("CUSTOMER"));
        rawUserEntity.setEnabled(true);
        rawUserEntity.setPassword(passwordEncoder.passwordEncoder().encode(userRegisterDTO.getPassword()));

        return userEntityMapper.toResponseDTO(userRepo.save(rawUserEntity));
    }

    @Override
    public UserResponseDTO registerAdmin(UserRegisterDTO userRegisterDTO) {
        UserEntity rawUserEntity = userEntityMapper.toEntity(userRegisterDTO);
        rawUserEntity.setRole(Set.of("ADMIN"));
        rawUserEntity.setEnabled(true);
        rawUserEntity.setPassword(passwordEncoder.passwordEncoder().encode(userRegisterDTO.getPassword()));

        return userEntityMapper.toResponseDTO(userRepo.save(rawUserEntity));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userRepo.existsByEmail(username))
            throw new UsernameNotFoundException("User not found with username/email: '" + username + "'");

        return userRepo.findByEmail(username);
    }

    @Override
    public List<UserResponseDTO> loadAllUsers() {
        List<UserEntity> savedUsers = userRepo.findAll();
        if (!savedUsers.isEmpty())
            return savedUsers.stream()
                    .filter(UserEntity::isEnabled)
                    .map(user -> UserResponseDTO.builder()
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .createdAt(formatter.formatToUTC(user.getCreatedAt()))
                            .updatedAt(formatter.formatToUTC(user.getUpdatedAt()))
                            .isActive(user.isEnabled())
                            .isEmailVerified(user.getRegistered() != null && user.getRegistered())
                            .build())
                    .toList();
        else return List.of(UserResponseDTO.builder().build());
    }

    @Override
    public UserResponseDTO findUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> savedUser = userRepo.findByUserName(username);

        if (savedUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username/email: '" + username + "'");
        }

        UserEntity user = savedUser.get();
        return UserResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(formatter.formatToUTC(user.getCreatedAt()))
                .updatedAt(formatter.formatToUTC(user.getUpdatedAt()))
                .isActive(user.isEnabled())
                .isEmailVerified(user.getRegistered() != null && user.getRegistered())
                .build();
    }
}
